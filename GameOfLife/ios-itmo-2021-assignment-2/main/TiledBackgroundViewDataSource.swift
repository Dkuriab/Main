//
//  TiledBackgroundViewDataSource.swift
//  Automat


import Foundation

protocol TiledBackgroundViewDataSource: AnyObject {
    var viewport: Rect { get }
    func getCellFromPoint(at point: Point) -> Bool
    func setNewState(from state: ElementaryCellularAutomata.State)
    func setTitle(from title: String)
}
