import Foundation

struct OneDimensionalCellularAutomata: CellularAutomata {
    typealias State = CellularAutomataStateImpl
    var rule: UInt8

    public init(rule: UInt8) {
        self.rule = rule
    }

    func simulate(_ state: CellularAutomataStateImpl, generations: UInt) throws -> CellularAutomataStateImpl {
        var nextState = state

        for _ in 0..<generations {

            let origin = nextState.viewport.origin
            let y = origin.y + nextState.viewport.size.height - 1


            for i in 0...nextState.viewport.size.width {
                let x = nextState.viewport.origin.x + i

                let a = nextState[Point(x: x - 1, y: y)].rawValue
                let b = nextState[Point(x: x, y: y)].rawValue
                let c = nextState[Point(x: x + 1, y: y)].rawValue

                let nextGenCell = BinaryCell(rawValue: 1 & (rule >> (a << 2 | b << 1 | c)))!
                nextState[Point(x: x, y: y + 1)] = nextGenCell
            }
        }

        return nextState
    }
}

struct CellularAutomataStateImpl: CellularAutomataState {
    typealias Cell = BinaryCell
    typealias SubState = CellularAutomataStateImpl

    var pointsState = Array(repeating: Array(repeating: Cell.inactive, count: 0), count: 0)
    private var _viewport: Rect

    /// Квадрат представляемой области в глобальных координатах поля
    /// Присвоение нового значение обрезая/дополняя поле до нужного размера
    var viewport: Rect {
        get {
            _viewport
        }
        set {
            let newPointsState = resize(for: newValue)
            pointsState = newPointsState
            _viewport = newValue
        }
    }

    init() {
        pointsState = []
        _viewport = Rect(origin: Point(x: 0, y: 0), size: Size(width: 0, height: 0))
    }

    init(pointsState: [[Cell]], viewport: Rect) {
        self.pointsState = pointsState
        _viewport = viewport
    }

    /// Значение конкретной ячейки в точке, заданной в глобальных координатах.
    subscript(_ point: Point) -> Cell {
        get {
            guard (0..<viewport.size.width ~= point.x) &&
                  (0..<viewport.size.height ~= point.y) else {
                return Cell.inactive
            }

            guard (0..<viewport.size.height ~= point.y - viewport.origin.y) &&
                  (0..<viewport.size.width ~= point.x - viewport.origin.x) else {
                return Cell.inactive
            }

            return pointsState[point.y - viewport.origin.y][point.x - viewport.origin.x]
        }
        set {
            if !viewport.contains(point) {
                let newViewport = viewport.resize(containing: point)
                viewport = newViewport
            }
            
            pointsState[point.y - viewport.origin.y][point.x - viewport.origin.x] = newValue
        }
    }

    /// Значение поля в прямоугольнике, заданном в глобальных координатах.
    subscript(_ rect: Rect) -> SubState {
        get {
            let answerPointsState = resize(for: rect)
            return SubState(pointsState: answerPointsState, viewport: rect)
        }
        set {
            let newValueOrigin = newValue.viewport.origin

            for x in 0..<newValue.viewport.size.width {
                for y in 0..<newValue.viewport.size.height {
                    let newX = newValueOrigin.x + x
                    let newY = newValueOrigin.y + y
                    
                    let point = Point(x: newX, y: newY)
                    self[point] = newValue.pointsState[y][x]
                }
            }
        }
    }
    
    public mutating func trim() {
        var top = Int.max
        var bottom = 0
        var left = Int.max
        var right = 0
        
        for y in 0..<viewport.size.height {
            for x in 0..<viewport.size.width {
            
                let point = Point(x: viewport.origin.x + x, y: viewport.origin.y + y)
                
                if pointsState[y][x] == .active {
                    top = min(point.y, top)
                    bottom = max(point.y, bottom)
                    left = min(point.x, left)
                    right = max(point.x, right)
                }
            }
        }
        

        top = top == Int.max ? 0 : top
        left = left == Int.max ? 0 : left
        let width = right - left + 1
        let heigh = bottom - top + 1
        
        let origin = Point(x: left, y: top)
                
        viewport = Rect(origin: origin, size: Size(width: width, height: heigh))
    }

    /// Меняет origin у viewport
    mutating func translate(to: Point) {
        viewport = Rect(origin: to, size: viewport.size)
    }

    public func resize(for newViewport: Rect) -> [[Cell]] {
        var newPointsState = Array(repeating: Array(repeating: Cell.inactive, count: newViewport.size.width), count: newViewport.size.height)

        for x in 0..<newViewport.size.width {
            for y in 0..<newViewport.size.height {
                let point = Point(x: newViewport.origin.x + x, y: newViewport.origin.y + y)

                newPointsState[y][x] = self[point]
            }
        }

        return newPointsState
    }
}
