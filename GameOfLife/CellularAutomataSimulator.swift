import Darwin

public protocol CellularAutomata {
    associatedtype State: CellularAutomataState

    /// Возвращает новое состояние поля после n поколений
    /// - Parameters:
    ///   - state: Исходное состояние поля
    ///   - generations: Количество симулирвемых поколений
    /// - Returns:
    ///   - Новое состояние после симуляции
    func simulate(_ state: State, generations: UInt) throws -> State
}
// аолпвралдоптывдапто
public protocol CellularAutomataState {
    associatedtype Cell
    associatedtype SubState: CellularAutomataState

    /// Конструктор пустого поля
    init()

    /// Квадрат представляемой области в глобальных координатах поля
    /// Присвоение нового значение обрезая/дополняя поле до нужного размера
    var viewport: Rect { get set }

    /// Значение конкретной ячейки в точке, заданной в глобальных координатах.
    subscript(_: Point) -> Cell { get set }
    /// Значение поля в прямоугольнике, заданном в глобальных координатах.
    subscript(_: Rect) -> SubState { get set }

    /// Меняет origin у viewport
    mutating func translate(to: Point)
}

public struct Size {
    public let width: Int
    public let height: Int

    public init(width: Int, height: Int) {
        guard width >= 0 && height >= 0 else {
            fatalError()
        }
        self.width = width
        self.height = height
    }
}

public struct Point {
    public let x: Int
    public let y: Int
}

public struct Rect {
    public let origin: Point
    public let size: Size
}

public enum BinaryCell: UInt8 {
    case inactive = 0
    case active = 1
}

extension Rect {
    public func contains(_ point: Point) -> Bool {
           point.x < origin.x + size.width
        && point.y < origin.y + size.height
        && point.x >= origin.x
        && point.y >= origin.y
    }

    public func resize(containing point: Point) -> Rect {
        if contains(point) {
            return self
        } else {
            let rightEdge = size.width + origin.x - 1
            let downEdge = size.height + origin.y - 1

            var newWidth = size.width
            var newHeight = size.height
            var newOriginX = origin.x
            var newOriginY = origin.y

            if point.x < origin.x {
                newWidth = newWidth + (origin.x - point.x)
                newOriginX = point.x
            } else if point.x > rightEdge {
                newWidth = point.x - origin.x + 1
            }

            if point.y < origin.y {
                newHeight = newHeight + (origin.y - point.y)
                newOriginY = point.y
            } else if point.y > downEdge {
                newHeight = point.y - origin.y + 1
            }


            let size = Size(width: newWidth, height: newHeight)
            let origin = Point(x: newOriginX, y: newOriginY)

            return Rect(origin: origin, size: size)
        }
    }
}
