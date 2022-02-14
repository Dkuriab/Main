import Foundation

struct TwoDimensionalCellularAutomata: CellularAutomata {
    typealias State = OneDimensionalCellularAutomata.State
    typealias Cell = BinaryCell
    let rule: ([[Cell]]) -> Cell

    init(rule: @escaping ([[Cell]]) -> Cell) {
        self.rule = rule
    }

    func simulate(_ state: State, generations: UInt) throws -> State {
        var curState = state

        for _ in 0..<generations {

            var origin = curState.viewport.origin
            var size = curState.viewport.size
            
            origin = curState.viewport.origin
            size = curState.viewport.size

            let copyState = curState
            for y in 0...size.height {
                for x in 0...size.width {
                    let curPoint = Point(x: origin.x + x, y: origin.y + y)

                    let matrix = copyState[Rect(origin: Point(x: curPoint.x - 1, y: curPoint.y - 1), size: Size(width: 3, height: 3))]
                    curState[curPoint] = rule(matrix.pointsState)

                }
            }
        }

        return curState
    }
}
