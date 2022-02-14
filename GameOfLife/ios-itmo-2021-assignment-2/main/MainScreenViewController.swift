import UIKit
import SnapKit

final class MainScreenViewController: UIViewController, TiledBackgroundViewDataSource {
    var viewport: Rect { self.mainState.viewport }
    var isSimulating = false
    var snapshots = [ElementaryCellularAutomata.State()]
    var automataType: String = "Game of Life"
    var rule: UInt8 = 30
    var tiledViewWidthConstraint: NSLayoutConstraint!
    var tiledViewHeightConstraint: NSLayoutConstraint!
    
    let scrollView = UIScrollView()
    var mainState: ElementaryCellularAutomata.State = ElementaryCellularAutomata.State()
    let tiledView: TiledBackgroundView = TiledBackgroundView(frame: .zero)
    let tileSize = 100
    
    var play: UIBarButtonItem!
    var pause: UIBarButtonItem!
    
    var selectView: UIView!
    var initialCenter = CGPoint()
    var finalCenter = CGPoint()
    var startPoint = CGPoint()
    var rectSize = CGSize()
    
    var isResizingUL = false
    var isResizingUR = false
    var isResizingDL = false
    var isResizingDR = false
    

    override func viewDidLoad() {
        super.viewDidLoad()
        self.view.backgroundColor = UIColor.systemGray6
        
        self.setupNavAndToolBar()
        self.setupScrollView()
        self.setupTiledView()
        self.setupConstraints()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setToolbarHidden(false, animated: animated)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.navigationController?.setToolbarHidden(true, animated: animated)
    }
}

extension MainScreenViewController {
    func getCellFromPoint(at point: Point) -> Bool {
        return self.mainState[point] == .active
    }
    
    func setNewState(from state: ElementaryCellularAutomata.State) {
        self.mainState = state
        self.drawState()
    }
    
    func setTitle(from title: String) {
        self.navigationItem.title = title
    }
}

extension MainScreenViewController {
    func resizeTiledView(width: CGFloat, height: CGFloat) {
        self.tiledViewWidthConstraint.constant = width
        self.tiledViewHeightConstraint.constant = height
    }
    
    func setupConstraints() {
        self.tiledViewWidthConstraint = self.tiledView.widthAnchor.constraint(equalToConstant: 1300)
        self.tiledViewHeightConstraint = self.tiledView.heightAnchor.constraint(equalToConstant: 3000)
        NSLayoutConstraint.activate([
            self.scrollView.topAnchor.constraint(equalTo: self.view.topAnchor),
            self.scrollView.bottomAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.bottomAnchor),
            self.scrollView.leadingAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.leadingAnchor),
            self.scrollView.trailingAnchor.constraint(equalTo: self.view.safeAreaLayoutGuide.trailingAnchor),
            
            self.tiledView.topAnchor.constraint(equalTo: self.scrollView.topAnchor),
            self.tiledView.bottomAnchor.constraint(equalTo: self.scrollView.bottomAnchor),
            self.tiledView.leadingAnchor.constraint(equalTo: self.scrollView.leadingAnchor),
            self.tiledView.trailingAnchor.constraint(equalTo: self.scrollView.trailingAnchor),
            self.tiledViewWidthConstraint,
            self.tiledViewHeightConstraint
        ])
    }
    
    func setupNavAndToolBar() {
        self.navigationItem.title = self.automataType
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "ellipsis"),
                                                                 style: .plain,
                                                                 target: self,
                                                                 action: #selector(self.showDropMenu(sender:)))
        
        self.navigationItem.rightBarButtonItem?.tintColor = .systemGray
        self.play = UIBarButtonItem(image: UIImage(systemName: "play"), style: .plain, target: self, action: #selector(self.playSimulateAutomata(sender:)))
        self.play.tintColor = .systemGray
        self.pause = UIBarButtonItem(image: UIImage(systemName: "pause"), style: .plain, target: self, action: #selector(self.stopSimulateAutomata(sender:)))
        self.pause.tintColor = .systemGray
        
        self.toolbarItems = [
            UIBarButtonItem(image: UIImage(systemName: "camera.viewfinder"), style: .plain, target: self, action: #selector(self.saveSnapshot(sender:))),
            UIBarButtonItem.flexibleSpace(),
            UIBarButtonItem(image: UIImage(systemName: "backward.end.alt"), style: .plain, target: self, action: #selector(self.rollbackToThePreviousSnapshot(sender:))),
            play,
            UIBarButtonItem(image: UIImage(systemName: "forward.end"), style: .plain, target: self, action: #selector(self.simulateOneGeneration(sender:))),
            UIBarButtonItem.flexibleSpace(),
            UIBarButtonItem(image: UIImage(systemName: "plus"), style: .plain, target: self, action: #selector(self.showLibraryViewController(sender:)))
        ]
    }
    
    func setupScrollView() {
        self.view.addSubview(scrollView)
        self.scrollView.translatesAutoresizingMaskIntoConstraints = false
        self.scrollView.delegate = self
        self.scrollView.minimumZoomScale = 0.01
        self.scrollView.maximumZoomScale = 5.0
        self.scrollView.zoomScale = 0.3
    }
    
    func setupTiledView() {
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(self.handleTap(_:)))
        let longTapGestureRecognizer = UILongPressGestureRecognizer(target: self, action: #selector(self.selectRect(sender:)))
        longTapGestureRecognizer.numberOfTouchesRequired = 1
        longTapGestureRecognizer.minimumPressDuration = TimeInterval(1.0)
        self.scrollView.addSubview(tiledView)
        self.tiledView.translatesAutoresizingMaskIntoConstraints = false
        self.tiledView.tiledLayer.tileSize = CGSize(width: 100, height: 100)
        self.tiledView.addGestureRecognizer(tapGestureRecognizer)
        self.tiledView.addGestureRecognizer(longTapGestureRecognizer)
        self.tiledView.dataSource = self
    }
    
    @objc func saveSnapshot(sender: UIAlertAction) {
        self.snapshots.append(self.mainState)
    }
    
    @objc func rollbackToThePreviousSnapshot(sender: UIAlertAction) {
        if (self.snapshots.count == 1) {
            let alertController = UIAlertController(title: "Нет последних сохраненных состояний", message: "Вы хотите вернуться к пустому полю?", preferredStyle: .alert)
            
            alertController.addAction(UIAlertAction(title: "Да", style: .destructive, handler: clearField(sender:)))
            alertController.addAction(UIAlertAction(title: "Нет", style: .default, handler: nil))
            
            self.present(alertController, animated: true, completion: nil)
        } else {
            self.mainState = self.snapshots.last ?? ElementaryCellularAutomata.State()
            self.snapshots.removeLast()
            self.tiledView.setNeedsDisplay()
        }
    }
    
    @objc func simulateOneGeneration(sender: UIAlertAction) {
        if (self.automataType == "Game of Life") {
            let twoDimAut = TwoDimentionsCellularAutomata(rule: self.ruleLife(vicinity:))
            self.mainState = try! twoDimAut.simulate(self.mainState, generations: 1)
            self.drawState()
        } else {
            let elementaryAutomata = ElementaryCellularAutomata(rule: self.rule)
            self.mainState = try! elementaryAutomata.simulate(self.mainState, generations: 1)
            self.drawState()
        }
    }
    
    func ruleLife(vicinity: ElementaryCellularAutomata.State) -> BinaryCell {
        var cnt: Int = 0
        let cell: BinaryCell = vicinity.array[4]
        for i in 0..<vicinity.array.count {
            if vicinity.array[i] == .active {
                cnt += 1
            }
        }
        if cell == .active {
            cnt -= 1
        }
        if cnt == 3 && cell == .inactive {
            return .active
        } else if (cnt == 2 || cnt == 3) && cell == .active {
            return .active
        } else {
            return .inactive
        }
    }
    
    func drawState() {
        for x in self.mainState.viewport.verticalIndexes {
            for y in self.mainState.viewport.horizontalIndexes {
                let rect = CGRect(x: x * self.tileSize, y: y * self.tileSize, width: self.tileSize, height: self.tileSize)
                self.tiledView.setNeedsDisplay(rect)
            }
        }
    }
    
    @objc func playSimulateAutomata(sender: UIBarButtonItem) {
        self.isSimulating = (self.isSimulating) ? false : true
        var items = self.navigationController?.toolbar.items!
        items?[3] = pause
        self.navigationController?.toolbar.setItems(items, animated: true)
        
        let twoDimAut = TwoDimentionsCellularAutomata(rule: self.ruleLife(vicinity:))
        let elementaryAut = ElementaryCellularAutomata(rule: self.rule)
        self.mainState[self.mainState.viewport.bottomRight + Point(x: 1, y: 1)] = .inactive
        Timer.scheduledTimer(withTimeInterval: 2.0, repeats: self.isSimulating, block: {timer in
            if (!self.isSimulating) {
                timer.invalidate()
            }
            if (self.automataType == "Game of Life") {
                self.mainState = try! twoDimAut.simulate(self.mainState, generations: 1)
            } else {
                self.mainState = try! elementaryAut.simulate(self.mainState, generations: 1)
            }
            self.drawState()
        })
    }
    
    @objc func stopSimulateAutomata(sender: UIBarButtonItem) {
        self.isSimulating = (self.isSimulating) ? false : true
        var items = self.navigationController?.toolbar.items!
        items?[3] = play
        self.navigationController?.toolbar.setItems(items, animated: true)
    }
    
    @objc func showDropMenu(sender: UIBarButtonItem) {
        let alertController = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)

        let options = UIAlertAction(title: "настроить текущий автомат", style: .default, handler: self.showAutomatesToPick(sender:))
        let optionsImg = UIImage(systemName: "gearshape")
        options.setValue(optionsImg, forKey: "image")
        alertController.addAction(options)

        let size = UIAlertAction(title: "изменить размеры поля", style: .default, handler: self.setBounds(sender:))
        let sizeImg = UIImage(systemName: "crop")
        size.setValue(sizeImg, forKey: "image")
        alertController.addAction(size)

        let clear = UIAlertAction(title: "очистить текущее поле", style: .destructive, handler: self.clearFieldAlert(sender:))
        let clearImg = UIImage(systemName: "xmark.octagon")
        clear.setValue(clearImg, forKey: "image")
        alertController.addAction(clear)
        
        let cancel = UIAlertAction(title: "Отменить", style: .cancel, handler: nil)
        alertController.addAction(cancel)
        
        alertController.popoverPresentationController?.sourceView = self.view
        alertController.popoverPresentationController?.barButtonItem = sender
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    func showAutomatesToPick(sender: UIAlertAction) {
        let alertController = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)

        let elementaryAutomata = UIAlertAction(title: "Элементарный автомат", style: .default, handler: self.setElementaryAutomata(sender:))
        alertController.addAction(elementaryAutomata)

        let gameLife = UIAlertAction(title: "Игра жизнь", style: .default, handler: self.setGameOfLifeAutomata(sender:))
        alertController.addAction(gameLife)
        
        let cancel = UIAlertAction(title: "Отменить", style: .cancel, handler: nil)
        alertController.addAction(cancel)
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    func setGameOfLifeAutomata(sender: UIAlertAction) {
        self.automataType = "Game of Life"
        self.navigationItem.title = self.automataType
        self.navigationItem.leftBarButtonItem = nil
    }
    
    func setElementaryAutomata(sender: UIAlertAction) {
        self.automataType = "Elementary Automata"
        self.navigationItem.title = self.automataType
        self.navigationItem.leftBarButtonItem = UIBarButtonItem(barButtonSystemItem: .edit, target: self, action: #selector(self.letEnterRule(sender:)))
    }
    
    @objc func letEnterRule(sender: UIBarButtonItem) {
        let alertController = UIAlertController(title: "Введите правило по коду Вольфрама", message: "Правило по умолчанию - 30", preferredStyle: .alert)
        
        alertController.addTextField(configurationHandler: {(textField: UITextField!) in
            textField.placeholder = "Введите правило"
        })
        
        alertController.addAction(UIAlertAction(title: "Ввод", style: .default, handler: { [weak alertController] (_) in
            self.rule = UInt8(alertController?.textFields![0].text ?? "0") ?? 0
        }))
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    func setBounds(sender: UIAlertAction) {
        let alertViewController = UIAlertController(title: "Введите новые размеры поля", message: nil, preferredStyle: .alert)
        
        alertViewController.addTextField(configurationHandler: {(textField: UITextField!) in
            textField.placeholder = "Введите ширину"
        })
        alertViewController.addTextField(configurationHandler: {(textField: UITextField!) in
            textField.placeholder = "Введите высоту"
        })
        alertViewController.addAction(UIAlertAction(title: "Ввод", style: .default, handler: { [weak alertViewController] (_) in
            let width = Int(alertViewController?.textFields![0].text ?? "0") ?? 0
            let height = Int(alertViewController?.textFields![1].text ?? "0") ?? 0
            self.resizeTiledView(width: CGFloat(width * self.tileSize), height: CGFloat(height * self.tileSize))
            self.tiledView.setNeedsDisplay()
        }))
        
        self.present(alertViewController, animated: true, completion: nil)
    }
    
    func clearFieldAlert(sender: UIAlertAction) {
        let clearAlert = UIAlertController(title: "Очистить поле?", message: "Все данные будут потеряны", preferredStyle: .alert)
        
        clearAlert.addAction(UIAlertAction(title: "Да", style: .destructive, handler: clearField(sender:)))
        clearAlert.addAction(UIAlertAction(title: "Нет", style: .default, handler: nil))
        
        self.present(clearAlert, animated: true, completion: nil)
    }
    
    func clearField(sender: UIAlertAction) {
        self.mainState = ElementaryCellularAutomata.State()
        self.resizeTiledView(width: 1300, height: 3000)
        mainState[Point(x: 13, y: 30)] = .inactive
        tiledView.setNeedsDisplay()
    }
    
    @objc func showLibraryViewController(sender: UIBarButtonItem) {
        let libraryVC = LibraryViewController()
        libraryVC.modalPresentationStyle = .pageSheet
        libraryVC.dataSource = self
        self.show(libraryVC, sender: sender)
    }
    
    @objc func handleTap(_ sender: UITapGestureRecognizer? = nil) {
        if (!self.isSimulating) {
            let point = sender?.location(in: tiledView)
            let x = Int(Int(point!.x) / self.tileSize)
            let y = Int(Int(point!.y) / self.tileSize)
            let automataPoint = Point(x: x, y: y)
            mainState[automataPoint] = (mainState[automataPoint] == .active) ? .inactive : .active
            tiledView.setNeedsDisplay(CGRect(origin: CGPoint(x: Int(automataPoint.x * self.tileSize), y: Int(automataPoint.y * self.tileSize)),
                                             size: CGSize(width: self.tileSize, height: self.tileSize)))
        }
    }
    
    // from
    
    @objc func selectRect(sender: UILongPressGestureRecognizer) {
        let point = sender.location(in: self.tiledView)
        let x = max(0, Int(point.x - 100))
        let y = max(0, Int(point.y - 100))
        
        self.selectView = UIView(frame: CGRect(x: x, y: y, width: 200, height: 200))
        self.selectView.backgroundColor = UIColor.systemBlue.withAlphaComponent(0.2)
        self.selectView.layer.borderColor = UIColor.systemBlue.withAlphaComponent(0.15).cgColor
        self.selectView.layer.borderWidth = 5
        self.selectView.layer.cornerRadius = 20
        self.selectView.translatesAutoresizingMaskIntoConstraints = false
        
        let panGestureRecognizer = UIPanGestureRecognizer(target: self, action: #selector(self.resizeSelectView(sender:)))
        self.selectView.addGestureRecognizer(panGestureRecognizer)
        self.tiledView.addSubview(self.selectView)
        sender.isEnabled = false
        
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(barButtonSystemItem: .done, target: self, action: #selector(self.removeSelection(sender:)))
        
        self.toolbarItems = [
            UIBarButtonItem.flexibleSpace(),
            UIBarButtonItem(image: UIImage(systemName: "square.and.arrow.down.on.square"), style: .plain, target: self, action: nil),
            UIBarButtonItem.flexibleSpace(),
            UIBarButtonItem.flexibleSpace(),
            UIBarButtonItem(image: UIImage(systemName: "square.slash"), style: .plain, target: self, action: #selector(self.clearViewport(sender:))),
            UIBarButtonItem.flexibleSpace()
        ]
    }
    
    @objc func removeSelection(sender: UIBarButtonItem) {
        self.navigationItem.title = self.automataType
        self.navigationItem.rightBarButtonItem = UIBarButtonItem(image: UIImage(systemName: "ellipsis"),
                                                                 style: .plain,
                                                                 target: self,
                                                                 action: #selector(self.showDropMenu(sender:)))
        
        self.toolbarItems = [
            UIBarButtonItem(image: UIImage(systemName: "camera.viewfinder"), style: .plain, target: self, action: #selector(self.saveSnapshot(sender:))),
            UIBarButtonItem.flexibleSpace(),
            UIBarButtonItem(image: UIImage(systemName: "backward.end.alt"), style: .plain, target: self, action: #selector(self.rollbackToThePreviousSnapshot(sender:))),
            play,
            UIBarButtonItem(image: UIImage(systemName: "forward.end"), style: .plain, target: self, action: #selector(self.simulateOneGeneration(sender:))),
            UIBarButtonItem.flexibleSpace(),
            UIBarButtonItem(image: UIImage(systemName: "plus"), style: .plain, target: self, action: #selector(self.showLibraryViewController(sender:)))
        ]
        
        self.selectView.removeFromSuperview()
        self.tiledView.gestureRecognizers?[1].isEnabled = true
        self.isResizingUL = false
        self.isResizingUR = false
        self.isResizingDL = false
        self.isResizingDR = false
    }
    
    @objc func clearViewport(sender: UIBarButtonItem) {
        
    }
    
    @objc func resizeSelectView(sender: UIPanGestureRecognizer) {
        let point = sender.location(in: self.tiledView)
        
        let translation = sender.translation(in: self.tiledView)
        if (sender.state == .began) {
            self.initialCenter = self.selectView.center
            self.startPoint = point
            self.rectSize = self.selectView.bounds.size
        }
        
        print(self.startPoint)
        let edgeUL = CGPoint(x: self.selectView.center.x - self.rectSize.width / 2, y: self.selectView.center.y - self.rectSize.height / 2)
        let edgeUR = CGPoint(x: self.selectView.center.x + self.rectSize.width / 2, y: self.selectView.center.y - self.rectSize.height / 2)
        let edgeDL = CGPoint(x: self.selectView.center.x - self.rectSize.width / 2, y: self.selectView.center.y + self.rectSize.height / 2)
        let edgeDR = CGPoint(x: self.selectView.center.x + self.rectSize.width / 2, y: self.selectView.center.y + self.rectSize.height / 2)
        
        if (self.startPoint.x <= edgeUL.x + 20 && self.startPoint.x >= edgeUL.x &&
            self.startPoint.y <= edgeUL.y + 20 && self.startPoint.y >= edgeUL.y) {
            self.isResizingUL = true
        } else if (self.startPoint.x >= edgeUR.x - 20 && self.startPoint.x <= edgeUR.x &&
                   self.startPoint.y <= edgeUR.y + 20 && self.startPoint.y >= edgeUR.y) {
            self.isResizingUR = true
        } else if (self.startPoint.x <= edgeDL.x + 20 && self.startPoint.x >= edgeDL.x &&
                   self.startPoint.y >= edgeDL.y - 20 && self.startPoint.y <= edgeDL.y) {
            self.isResizingDL = true
        } else if (self.startPoint.x >= edgeDR.x - 20 && self.startPoint.x <= edgeDR.x &&
                   self.startPoint.y >= edgeDR.y - 20 && self.startPoint.y <= edgeDR.y) {
            self.isResizingDR = true
        }
        
        if (sender.state != .cancelled) {
            if (isResizingUL) {
                let delta = CGPoint(x: point.x - edgeUL.x, y: point.y - edgeUL.y)
                self.selectView.frame = CGRect(x: Int(edgeUL.x - delta.x),
                                               y: Int(edgeUL.y - delta.y),
                                               width: Int(self.selectView.bounds.size.width + delta.x),
                                               height: Int(self.selectView.bounds.size.height + delta.y))
            } else {
                let newCenter = CGPoint(x: initialCenter.x + translation.x, y: initialCenter.y + translation.y)
                self.finalCenter = newCenter
                self.selectView.center = newCenter
            }
        } else {
            self.selectView.center = self.initialCenter
        }
        
        if (sender.state == .ended) {
            let x = lroundf(Float(self.finalCenter.x / 100)) * 100
            let y = lroundf(Float(self.finalCenter.y / 100)) * 100
            self.selectView.center = CGPoint(x: x, y: y)
        }
    }
    
    //to
}

extension MainScreenViewController: UIScrollViewDelegate {
    func viewForZooming(in scrollView: UIScrollView) -> UIView? {
        tiledView
    }
}

class TiledBackgroundView: UIView {
    let sideLength: CGFloat = 30
    public weak var dataSource: TiledBackgroundViewDataSource?
    
    override class var layerClass: AnyClass { CATiledLayer.self }
    
    var tiledLayer: CATiledLayer { layer as! CATiledLayer }
    
    override var contentScaleFactor: CGFloat {
        didSet { super.contentScaleFactor = 1 }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func draw(_ rect: CGRect) {
        guard let context = UIGraphicsGetCurrentContext() else { fatalError("skldjflsdjf") }
        context.setFillColor(UIColor.systemGray6.cgColor)
        context.fill(rect)
        
        if (dataSource?.getCellFromPoint(at: Point(x: Int(rect.minX / 100), y: Int(rect.minY / 100))) ?? false) {
            context.setFillColor(UIColor.systemGray.cgColor)
        } else {
            context.setFillColor(UIColor.systemGray5.cgColor)
        }
        context.fill(rect.inset(by: UIEdgeInsets(top: 3, left: 3, bottom: 3, right: 3)))
    }
}
