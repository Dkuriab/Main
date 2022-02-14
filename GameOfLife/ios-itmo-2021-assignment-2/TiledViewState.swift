//
//  TiledViewState.swift
//  ios-itmo-2021-assignment-2
//
//  Created by Danila on 18.01.2022.
//

import Foundation
import UIKit

protocol TiledViewState: AnyObject {
    var automataState: CellularAutomataStateImpl { get set }
    var scrollView: UIScrollView! { get set }
}
