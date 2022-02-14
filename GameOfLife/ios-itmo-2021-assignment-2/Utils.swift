//
//  Utils.swift
//  ios-itmo-2021-assignment-2
//
//  Created by Danila on 16.01.2022.
//

import Foundation
import UIKit

func rule(state: [[BinaryCell]]) -> BinaryCell {
    let directions = [(-1, -1), (0, -1), (1, -1),
                      (-1, 0),            (1, 0),
                      (-1, 1), (0, 1),    (1, 1), ]

    var aliveCount = 0;
    for (m, n) in directions {
        aliveCount += Int(state[1 + n][1 + m].rawValue)
    }

    switch (state[1][1], aliveCount) {
    case (.active, 2...3), (.inactive, 3):
        return .active
    default:
        return .inactive
    }
}

enum State {
    case closed
    case open
}

extension State {
    var opposite: State {
        switch self {
        case .open: return .closed
        case .closed: return .open
        }
    }
}

enum AutomataType {
    case onedimensional, twodimensional
}

struct ResizeTap{
    var topTouch = false
    var leftTouch = false
    var rightTouch = false
    var bottomTouch = false
    var middelTouch = false
}

class StableTiledViewState: TiledViewState {
    var automataState: CellularAutomataStateImpl
    var scrollView: UIScrollView!
    
    init(automataState: CellularAutomataStateImpl, scrollView: UIScrollView) {
        self.automataState = automataState
        self.scrollView = scrollView
    }
}

func showScreenshotEffect(view: UIView) {
    let snapshotView = UIView()
    snapshotView.translatesAutoresizingMaskIntoConstraints = false
    view.addSubview(snapshotView)
    
    let constraints:[NSLayoutConstraint] = [
        snapshotView.topAnchor.constraint(equalTo: view.topAnchor),
        snapshotView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
        snapshotView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
        snapshotView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor)
    ]
    NSLayoutConstraint.activate(constraints)
    snapshotView.backgroundColor = UIColor.white
    UIView.animate(withDuration: 0.2, animations: {
        snapshotView.alpha = 0
    }) { _ in
        snapshotView.removeFromSuperview()
    }
}

func rotateMatrixLeft<T>(matrixInput: [[T]]) -> [[T]] {
    return rotateMatrixRight(matrixInput:
                                rotateMatrixRight(matrixInput:
                                                    rotateMatrixRight(matrixInput: matrixInput))
    )
}
func rotateMatrixRight<T>(matrixInput: [[T]]) -> [[T]] {

    let matrix = matrixTranspose(matrixInput);
    
    var answer: [[T]] = []
    
    for line in matrix {
        answer.append(line.reversed())
    }

    return answer
}

func matrixTranspose<T>(_ matrix: [[T]]) -> [[T]] {
    if matrix.isEmpty {return matrix}
    var result = [[T]]()
    for index in 0..<matrix.first!.count {
        result.append(matrix.map{$0[index]})
    }
    return result
}
