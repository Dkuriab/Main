//
//  LibraryViewController.swift
//  Automat

import UIKit
import SnapKit

class LibraryViewController: UIViewController {
    public weak var dataSource: TiledBackgroundViewDataSource?
    
    lazy var tableView: UITableView = {
        let tableView = UITableView()
        tableView.dataSource = self
        tableView.delegate = self
        tableView.register(LibraryTableViewCell.self, forCellReuseIdentifier: NSStringFromClass(LibraryTableViewCell.self))
        tableView.rowHeight = view.bounds.height/8
        tableView.largeContentTitle = "Library"
        return tableView
    }()

    let data = ["Simple Glider", "Triangle", "Two Triangles"]

    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor(red: 255, green: 255, blue: 255, alpha: 1.0)
        setupTableView()
        
    }

    func setupTableView() {
        view.addSubview(tableView)
        tableView.snp.makeConstraints { make in
            make.top.equalToSuperview()
            make.bottom.equalToSuperview()
            make.left.equalToSuperview()
            make.right.equalToSuperview()
        }
    }
}

extension LibraryViewController: UITableViewDataSource, UITableViewDelegate {
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = self.tableView.dequeueReusableCell(withIdentifier: NSStringFromClass(LibraryTableViewCell.self), for: indexPath) as! LibraryTableViewCell
        cell.title.text = "\(data[indexPath.row])"
        cell.imageController.image = UIImage(named: "\(data[indexPath.row])")
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        var state = ElementaryCellularAutomata.State()
        switch "\(data[indexPath.row])" {
            case "Simple Glider":
                state[Point(x: 1, y: 0)] = .active
                state[Point(x: 2, y: 1)] = .active
                state[Point(x: 0, y: 2)] = .active
                state[Point(x: 1, y: 2)] = .active
                state[Point(x: 2, y: 2)] = .active
            case "Triangle":
                state[Point(x: 0, y: 1)] = .active
                state[Point(x: 1, y: 0)] = .active
                state[Point(x: 1, y: 1)] = .active
                state[Point(x: 2, y: 1)] = .active
            default:
                state[Point(x: 0, y: 1)] = .active
                state[Point(x: 1, y: 0)] = .active
                state[Point(x: 1, y: 1)] = .active
                state[Point(x: 2, y: 1)] = .active
                
                state[Point(x: 4, y: 1)] = .active
                state[Point(x: 5, y: 0)] = .active
                state[Point(x: 5, y: 1)] = .active
                state[Point(x: 6, y: 1)] = .active
        }
        self.dataSource?.setNewState(from: state)
        self.dataSource?.setTitle(from: "\(data[indexPath.row])")
        navigationController?.popViewController(animated: true)
    }
}

class LibraryTableViewCell: UITableViewCell {
    lazy var container: UIView = {
        let view = UIView()
        view.layer.cornerRadius = 8
        view.layer.masksToBounds = true
        return view
    }()
    lazy var title: UILabel = {
        let label = UILabel()
        return label
    }()
    lazy var imageController: UIImageView = {
        let image = UIImageView()
        return image
    }()
    
    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: style, reuseIdentifier: reuseIdentifier)
        setupContainer()
        setupImage()
        setupTitle()
    }

    func setupContainer() {
        contentView.addSubview(container)
        container.snp.makeConstraints { make in
            make.top.equalTo(contentView).inset(2)
            make.left.equalTo(contentView).inset(2)
            make.right.equalTo(contentView).inset(2)
            make.bottom.equalTo(contentView).inset(2)

        }
    }

    func setupImage() {
        container.addSubview(imageController)
        imageController.snp.makeConstraints { make in
            make.top.equalTo(contentView)
            make.bottom.equalTo(contentView)
            make.right.equalTo(contentView.snp.left).inset(contentView.bounds.width/5)
            make.left.equalTo(contentView.snp.left)
        }
    }

    func setupTitle() {
        container.addSubview(title)
        title.snp.makeConstraints { make in
            make.centerY.equalTo(imageController.snp.centerY)
            make.left.equalTo(imageController.snp.right).inset(-10)
            make.right.equalTo(contentView)
        }
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}


