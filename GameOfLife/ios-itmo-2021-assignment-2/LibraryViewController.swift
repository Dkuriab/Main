//
//  LibraryViewController.swift
//  ios-itmo-2021-assignment-2
//
//  Created by Danila on 18.01.2022.
//

import Foundation
import UIKit

class LibraryViewController: UIViewController {

    var tableView: UITableView!
    
    public weak var curState: MainScreenViewController!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white
        
        setUpViews()
    }
    
    private func setUpViews() {
        tableView = UITableView(frame: view.bounds)
        view.addSubview(tableView)
        tableView.dataSource = self
        tableView.delegate = self
        tableView.register(AutomataStateCell.self, forCellReuseIdentifier: "Cell")
        
        NSLayoutConstraint.activate([
            tableView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            tableView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            tableView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            tableView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor)
        ])
    }

}

extension LibraryViewController : UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return curState?.libraryStates.count ?? 0
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCell(withIdentifier: "Cell") as? AutomataStateCell {
            if let state = curState?.libraryStates[indexPath.row] {
                cell.setState(newValue: state)
                cell.setNeedsDisplay()
                return cell
            }
        }

        return UITableViewCell()
    }
}

extension LibraryViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        
        let cell = tableView.cellForRow(at: indexPath) as! AutomataStateCell
        curState.insertMode(insertState: cell.tiledView.curState as! StableTiledViewState)
        _ = navigationController?.popViewController(animated: true)
    }
}
class AutomataStateCell : UITableViewCell {
    var tiledView: TiledView!
    var tiledViewHeightConstraint: NSLayoutConstraint!
    var tiledViewWidthConstraint: NSLayoutConstraint!
    
    func setState(newValue: StableTiledViewState) {

        tiledView.curState = newValue
        
        let size = newValue.automataState.viewport.size
        let cellSize = 100 / size.height
    
        tiledView.tiledLayer.tileSize = CGSize(width: cellSize, height: cellSize)
        tiledViewWidthConstraint.constant = CGFloat(size.width * cellSize)
        tiledViewHeightConstraint.constant = CGFloat(size.height * cellSize)
    }
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        
        tiledView = TiledView()
        tiledView.translatesAutoresizingMaskIntoConstraints = false
        
        addSubview(tiledView)
        
        tiledViewHeightConstraint = tiledView.heightAnchor.constraint(equalToConstant: 100)
        tiledViewWidthConstraint = tiledView.widthAnchor.constraint(equalToConstant: 100)
        
        NSLayoutConstraint.activate([
            heightAnchor.constraint(equalToConstant: 120),
            
            tiledView.centerXAnchor.constraint(equalTo: self.centerXAnchor),
            tiledView.centerYAnchor.constraint(equalTo: self.centerYAnchor),
            tiledViewHeightConstraint,
            tiledViewWidthConstraint
        ])
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
