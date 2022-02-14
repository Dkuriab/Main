import UIKit

class MainScreenViewController: UIViewController, TiledViewState {
    
    var tiledView: TiledView!
    var scrollView: UIScrollView!
    
    var selectionItems: [UIBarButtonItem] {
        [
            UIBarButtonItem(image: UIImage(systemName: "pause"), style: .plain, target: self, action: #selector(pauseSimulation)),
            UIBarButtonItem(image: UIImage(systemName: "play"), style: .plain, target: self, action: #selector(startSimulation))
        ]
    }
    
    var rotateLeft: UIBarButtonItem!
    var rotateRight: UIBarButtonItem!
    var insertAlive: UIBarButtonItem!
    var insertAll: UIBarButtonItem!
    var save: UIBarButtonItem!
    var clean: UIBarButtonItem!
    var done: UIBarButtonItem!
    var settings: UIBarButtonItem!
    var start: UIBarButtonItem!
    var pause: UIBarButtonItem!
    var step: UIBarButtonItem!
    var library: UIBarButtonItem!
    var backToLastSnapshot: UIBarButtonItem!
    var takeSnapshot: UIBarButtonItem!
    
    var automataState = CellularAutomataStateImpl()
    var automataType = AutomataType.twodimensional
    var twoDimensionalAutomata: TwoDimensionalCellularAutomata?
    var oneDimentionalAutomata: OneDimensionalCellularAutomata?
    var snapshots: [CellularAutomataStateImpl] = []
    var libraryStates: [StableTiledViewState] = []
    var insertingState: CellularAutomataStateImpl?
    
    var tiledViewWidthConstraint: NSLayoutConstraint!
    var tiledViewHeightConstraint: NSLayoutConstraint!

    
    var topConstraint: NSLayoutConstraint!
    var rightConstraint: NSLayoutConstraint!
    var leftConstraint: NSLayoutConstraint!
    var bottomConstraint: NSLayoutConstraint!
    
    var timer: Timer?
    private var currentLibraryState: State = .closed
    let cellSize = 50
    let libraryViewHeight = CGFloat(300)
    
    override init(nibName nibNameOrNil: String?, bundle nibBundleOrNil: Bundle?) {

//        self.settings = UIBarButtonItem(image: UIImage(systemName: "gearshape"), style: .plain)
        
        super.init(nibName: nibNameOrNil, bundle: nibBundleOrNil)
        
//        self.settings.target = self
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    var _height = 30
    var tableHeight: Int {
        set {
            _height = newValue
            tiledViewHeightConstraint.constant = CGFloat(newValue * cellSize)
        }
        get {
            return _height
        }
    }
    
    var _width = 15
    var tableWidth: Int {
        set {
            _width = newValue
            tiledViewWidthConstraint.constant = CGFloat(newValue * cellSize)
        }
        get {
            return _width
        }
    }

    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setToolbarHidden(false, animated: animated)
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white
        
        setUpViews()
        setUpListeners()
        setUpToolbars()
        
        twoDimensionalAutomata = TwoDimensionalCellularAutomata(rule: rule)
        oneDimentionalAutomata = OneDimensionalCellularAutomata(rule: 30)
    }
    
    func setUpToolbars() {
        navigationItem.title = "Game Of Life"
        navigationItem.leftBarButtonItem = .none
        navigationItem.rightBarButtonItem = settings
        toolbarItems = [
            takeSnapshot,
            UIBarButtonItem.flexibleSpace(),
            backToLastSnapshot,
            start,
            step,
            UIBarButtonItem.flexibleSpace(),
            library
        ]
    }
    
    func setUpListeners() {
        let tapGestureRecognizer = UITapGestureRecognizer(target: self, action: #selector(onClickListener))
        let longPressGestureRecogmizer = UILongPressGestureRecognizer(target: self, action: #selector(onLongPressListener))
        tiledView.curState = self
        tiledView.addGestureRecognizer(tapGestureRecognizer)
        tiledView.addGestureRecognizer(longPressGestureRecogmizer)
    }
    
    func setUpViews() {
        settings =              UIBarButtonItem(image: UIImage(systemName: "gearshape"), style: .plain, target: self, action: #selector(showSettings))
        pause =                 UIBarButtonItem(image: UIImage(systemName: "pause"), style: .plain, target: self, action: #selector(pauseSimulation))
        start =                 UIBarButtonItem(image: UIImage(systemName: "play"), style: .plain, target: self, action: #selector(startSimulation))
        step =                  UIBarButtonItem(image: UIImage(systemName: "forward.end"), style: .plain, target: self, action: #selector(simulateOneStep))
        library =               UIBarButtonItem(image: UIImage(systemName: "circle.grid.cross.right.filled"), style: .plain, target: self, action: #selector(openLibrary))
        backToLastSnapshot =    UIBarButtonItem(image: UIImage(systemName: "backward.end.alt"), style: .done, target: self, action: #selector(backToSnapshot))
        takeSnapshot =          UIBarButtonItem(image: UIImage(systemName: "camera.shutter.button"), style: .plain, target: self, action: #selector(saveSnapshot))
        save =                  UIBarButtonItem(image: UIImage(systemName: "rectangle.stack.badge.plus"), style: .plain, target: self, action: #selector(saveSelectedToLibrary))
        clean =                 UIBarButtonItem(image: UIImage(systemName: "trash"), style: .plain, target: self, action: #selector(cleanSelected))
        done =                  UIBarButtonItem(image: UIImage(systemName: "chevron.left"), style: .plain, target: self, action: #selector(quitSelection))
        insertAll =             UIBarButtonItem(image: UIImage(systemName: "rectangle.on.rectangle"), style: .plain, target: self, action: #selector(insertState))
        insertAlive =           UIBarButtonItem(image: UIImage(systemName: "rectangle.on.rectangle.fill"), style: .plain, target: self, action: #selector(insertAliveState))
        rotateRight =           UIBarButtonItem(image: UIImage(systemName: "rotate.right"), style: .plain, target: self, action: #selector(rotateSelectedRight))
        rotateLeft =            UIBarButtonItem(image: UIImage(systemName: "rotate.left"), style: .plain, target: self, action: #selector(rotateSelectedLeft))
        
        backToLastSnapshot.isEnabled = false

        tiledView =     TiledView()
        scrollView =    UIScrollView()
        view        .addSubview(scrollView)
        scrollView  .addSubview(tiledView)
        
        scrollView.delegate = self
        scrollView.minimumZoomScale = 0.1
        scrollView.maximumZoomScale = 3.0
        scrollView.zoomScale = 1
        
        scrollView.translatesAutoresizingMaskIntoConstraints = false
        tiledView.translatesAutoresizingMaskIntoConstraints = false
        
        tiledView.tiledLayer.tileSize = CGSize(width: cellSize, height: cellSize)
        tiledViewWidthConstraint = tiledView.widthAnchor.constraint(equalToConstant: CGFloat(cellSize * tableWidth))
        tiledViewHeightConstraint = tiledView.heightAnchor.constraint(equalToConstant: CGFloat(cellSize * tableHeight))
        
        NSLayoutConstraint.activate([
            scrollView.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
            scrollView.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
            scrollView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            scrollView.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
            
            tiledView.leadingAnchor.constraint(equalTo: scrollView.leadingAnchor),
            tiledView.trailingAnchor.constraint(equalTo: scrollView.trailingAnchor),
            tiledView.topAnchor.constraint(equalTo: scrollView.topAnchor),
            tiledView.bottomAnchor.constraint(equalTo: scrollView.bottomAnchor),
            
            tiledViewWidthConstraint,
            tiledViewHeightConstraint
        ])
    }
    
    @objc func onLongPressListener(sender: UILongPressGestureRecognizer) {
        guard tiledView.insertView.isHidden else { return }
        
        let point = sender.location(in: tiledView)
        let x = Int(point.x) / cellSize
        let y = Int(point.y) / cellSize
        
        tiledView.selectView.frame = CGRect(x: x * cellSize, y: y * cellSize, width: cellSize * 2, height: cellSize * 2)
        tiledView.selectView.isHidden = false

        setUpSelectionToolbars()
    }
    
    func insertMode(insertState: StableTiledViewState) {
        let insertTiledView = TiledView()
        insertingState = insertState.automataState
        insertTiledView.curState = insertState
        let size = insertState.automataState.viewport.size
        insertTiledView.tiledLayer.tileSize = CGSize(width: cellSize, height: cellSize)
        insertTiledView.alpha = 0.7
        insertTiledView.backgroundColor = .white
        
        insertTiledView.translatesAutoresizingMaskIntoConstraints = false
        insertTiledView.isUserInteractionEnabled = false

        for view in tiledView.insertView.subviews {
            view.removeFromSuperview()
        }
        tiledView.insertView.addSubview(insertTiledView)
        tiledView.insertView.frame = CGRect(origin: CGPoint(x: -5, y: -5), size: CGSize(width: CGFloat(cellSize * size.width + 10), height: CGFloat(cellSize * size.height + 10)))
        tiledView.insertView.isHidden = false
        
        
        NSLayoutConstraint.activate([
            insertTiledView.topAnchor.constraint(equalTo: tiledView.insertView.topAnchor, constant: 5),
            insertTiledView.bottomAnchor.constraint(equalTo: tiledView.insertView.bottomAnchor, constant: -5),
            insertTiledView.leadingAnchor.constraint(equalTo: tiledView.insertView.leadingAnchor, constant: 5),
            insertTiledView.trailingAnchor.constraint(equalTo: tiledView.insertView.trailingAnchor, constant: -5)
        ])
        
        setUpInsertionToolbars()
    }
    
    @objc func quitSelection() {
        tiledView.selectView.isHidden = true
        tiledView.insertView.isHidden = true
        setUpToolbars()
    }
    
    @objc func insertState() {
        if insertingState != nil {
            let origin = tiledView.insertView.frame.origin
            let size = insertingState!.viewport.size
            
            let rect = Rect(
                origin: Point(x: Int(origin.x + 5) / cellSize, y: Int(origin.y + 5) / cellSize),
                size: size
            )
            let pointsState = insertingState!.pointsState
            let substate = CellularAutomataStateImpl(pointsState: pointsState, viewport: rect)
            
            automataState[rect] = substate
            tiledView.setNeedsDisplay(tiledView.insertView.frame)
        }
    }
    
    @objc func insertAliveState() {
        if insertingState != nil {
            let origin = tiledView.insertView.frame.origin
            let size = insertingState!.viewport.size
            
            let rect = Rect(
                origin: Point(x: Int(origin.x + 5) / cellSize, y: Int(origin.y + 5) / cellSize),
                size: size
            )
            let pointsState = insertingState!.pointsState
            
            for x in 0..<rect.size.width {
                for y in 0..<rect.size.height {
                    let realX = rect.origin.x + x
                    let realY = rect.origin.y + y
                    
                    if pointsState[y][x] == .active {
                        automataState[Point(x: realX, y: realY)] = .active
                    }
                }
            }
            
            tiledView.setNeedsDisplay(tiledView.insertView.frame)
        }
    }
    
    @objc func cleanSelected() {
        let origin = tiledView.selectView.frame.origin
        let size = tiledView.selectView.frame.size
        let rect = Rect(
            origin: Point(x: Int(origin.x) / cellSize, y: Int(origin.y) / cellSize),
            size: Size(width: Int(size.width) / cellSize , height: Int(size.height) / cellSize)
        )
        let pointsState = Array(repeating: Array(repeating: BinaryCell.inactive, count: Int(size.width) / cellSize), count: Int(size.height) / cellSize)
        let substate = CellularAutomataStateImpl(pointsState: pointsState, viewport: rect)

        automataState[rect] = substate
        tiledView.setNeedsDisplay()
    }
    
    @objc func saveSelectedToLibrary() {
        let origin = tiledView.selectView.frame.origin
        let size = tiledView.selectView.frame.size
        let rect = Rect(
            origin: Point(x: Int(origin.x) / cellSize, y: Int(origin.y) / cellSize),
            size: Size(width: Int(size.width) / cellSize , height: Int(size.height) / cellSize)
        )
        let newValue = automataState[rect]
        
        let values = newValue.pointsState
        let frame = Rect(origin: Point(x: 0, y: 0), size: newValue.viewport.size)
        let state = CellularAutomataStateImpl(pointsState: values, viewport: frame)
        let stableState = StableTiledViewState(automataState: state, scrollView: UIScrollView())
        
        libraryStates.append(stableState)
    }
    
    @objc func rotateSelectedLeft() {
        rotateWith(f: rotateMatrixLeft(matrixInput:))
    }
    
    @objc func rotateSelectedRight() {
        rotateWith(f: rotateMatrixRight(matrixInput:))
    }
    
    private func rotateWith(f: ([[BinaryCell]]) -> [[BinaryCell]]) {
        let origin = tiledView.selectView.frame.origin
        let size = tiledView.selectView.frame.size
        let rect = Rect(
            origin: Point(x: Int(origin.x) / cellSize, y: Int(origin.y) / cellSize),
            size: Size(width: Int(size.width) / cellSize , height: Int(size.height) / cellSize)
        )
        
        let value = automataState[rect]
        
        let newViewport = Rect(origin: rect.origin, size: Size(width: rect.size.height, height: rect.size.width))
        let newPointsState = f(value.pointsState)
        let newState = CellularAutomataStateImpl(pointsState: newPointsState, viewport: newViewport)
        let newRect = Rect(origin: rect.origin, size: Size(width: rect.size.height, height: rect.size.width))
        
        let empty = Array(repeating: Array(repeating: BinaryCell.inactive, count: Int(size.width) / cellSize), count: Int(size.height) / cellSize)
        let emptyState = CellularAutomataStateImpl(pointsState: empty, viewport: rect)

        automataState[rect] = emptyState
        automataState[newRect] = newState
        
        tiledView.selectView.frame = CGRect(
            origin: tiledView.selectView.frame.origin,
            size: CGSize(width: tiledView.selectView.frame.height, height: tiledView.selectView.frame.width)
        )

        tiledView.setNeedsDisplay()
    }
    
    private func setUpSelectionToolbars() {
        navigationItem.title = "Selection"
        navigationItem.leftBarButtonItem = done
        navigationItem.rightBarButtonItem = .none
        toolbarItems = [
            UIBarButtonItem.flexibleSpace(),
            clean,
            UIBarButtonItem.flexibleSpace(),
            save,
            UIBarButtonItem.flexibleSpace(),
            rotateRight,
            rotateLeft,
            UIBarButtonItem.flexibleSpace(),
        ]
    }
    
    private func setUpInsertionToolbars() {
        navigationItem.title = "Insert"
        navigationItem.leftBarButtonItem = done
        navigationItem.rightBarButtonItem = .none
        toolbarItems = [
            UIBarButtonItem.flexibleSpace(),
            insertAll,
            UIBarButtonItem.flexibleSpace(),
            insertAlive,
            UIBarButtonItem.flexibleSpace()
        ]
    }
    
    @objc func showSettings() {
        let alert = UIAlertController(
            title: "Settings",
            message: nil,
            preferredStyle: .actionSheet
        )
        
        alert.addAction(
            .init(title: "Choose automata", style: .default, handler: chooseAutomataAlert)
        )
        
        alert.addAction(
            .init(title: "Change table size", style: .default, handler: setTableSizesAlert)
        )
        
        alert.addAction(
            .init(title: "Clear table", style: .destructive, handler: clearTableAlert)
        )

        alert.addAction(
            .init(title: "Back", style: .cancel) { _ in }
        )

        present(alert, animated: true)
    }
    
    func setTableSizesAlert(sender: UIAlertAction) {
        let alert = UIAlertController (
            title: "Set table sizes",
            message: nil,
            preferredStyle: .alert
        )
        
        alert.addTextField{ (textField) in
            textField.placeholder = "width"
        }
        
        alert.addTextField{ (textField) in
            textField.placeholder = "heigh"
        }
        
        alert.addAction(
            .init(title: "OK", style: .default, handler: { [self, alert] (_) in
                let width = Int(alert.textFields![0].text ?? "30") ?? 30
                let height = Int(alert.textFields![1].text ?? "30") ?? 50
                
                tableWidth = width
                tableHeight = height
                tiledView.setNeedsDisplay()
        }))
        
        present(alert, animated: true)
    }
    
    func setWolframCodeAlert(sender: UIAlertAction) {
        let alert = UIAlertController (
            title: "Set Rule for automata",
            message: "0..255, 255 default",
            preferredStyle: .alert
        )
        
        alert.addTextField{ (textField) in
            textField.text = "30"
        }
        
        alert.addAction(
            .init(title: "OK", style: .default, handler: { [self, alert] (_) in
                let textField = alert.textFields![0]
                automataType = .onedimensional
                oneDimentionalAutomata = OneDimensionalCellularAutomata(rule: UInt8(textField.text ?? "255") ?? 255)
        }))
        
        present(alert, animated: true)
    }
    
    func clearTableAlert(sender: UIAlertAction) {
        let clearAlert = UIAlertController(title: "Clear table?", message: nil, preferredStyle: .alert)

        clearAlert.addAction(
            .init(title: "Yes", style: .destructive) { [self] _ in
                automataState = CellularAutomataStateImpl()
                tiledView.setNeedsDisplay()
            }
        )
        clearAlert.addAction(.init(title: "No", style: .default, handler: nil))

        self.present(clearAlert, animated: true, completion: nil)
    }
    
    func chooseAutomataAlert(sender: UIAlertAction) {
        let alert = UIAlertController(
            title: nil,
            message: nil,
            preferredStyle: .actionSheet
        )
        
        alert.addAction(
            .init(title: "One Dimentional Automata", style: .default, handler: setWolframCodeAlert)
        )
        
        alert.addAction(
            .init(title: "Two Dimentional Automata", style: .default) { [self] _ in
                automataType = .twodimensional
            }
        )
        
        present(alert, animated: true)
    }
    
    @objc func openLibrary(sender: UIBarButtonItem) {
        let library = LibraryViewController()
        library.modalPresentationStyle = .none
        library.curState = self
        self.show(library, sender: sender)
    }
    
    @objc func pauseSimulation() {
        timer?.invalidate()
        toolbarItems?[3] = start
        step.isEnabled = true
    }
    
    @objc func startSimulation() {
        timer = Timer.scheduledTimer(timeInterval: 0.4, target: self, selector: #selector(simulateOneStep), userInfo: nil, repeats: true)
        toolbarItems?[3] = pause
        step.isEnabled = false
    }
    
    @objc func backToSnapshot() {
        if (!snapshots.isEmpty) {
            automataState = snapshots.removeLast()
            backToLastSnapshot.isEnabled = !snapshots.isEmpty
            tiledView.setNeedsDisplay()
        }
    }
    
    @objc func saveSnapshot() {
        snapshots.append(automataState)
        showScreenshotEffect(view: view)
        backToLastSnapshot.isEnabled = true
    }
    
    @objc func simulateOneStep() {
        do {
            var newState: CellularAutomataStateImpl
            switch automataType {
            case .twodimensional:
                newState = try twoDimensionalAutomata!.simulate(automataState, generations: 1)
                break
            case .onedimensional:
                newState = try oneDimentionalAutomata!.simulate(automataState, generations: 1)
                break
            }
            
            automataState = newState
            tiledView.setNeedsDisplay()
        } catch _ {
            return
        }
        
    }
                        
    @objc func onClickListener(_ sender: UITapGestureRecognizer? = nil) {
        guard let point = sender?.location(in: tiledView) else { return }
        let x = Int(point.x) / cellSize
        let y = Int(point.y) / cellSize
        let pointReal = Point(x: x, y: y)
        
        automataState[pointReal] = automataState[pointReal] == .active ? .inactive : .active
        let rect = CGRect(origin: CGPoint(x: x * cellSize, y: y * cellSize), size: CGSize(width: cellSize, height: cellSize))
        tiledView.setNeedsDisplay(rect)
    }
}

extension MainScreenViewController: UIScrollViewDelegate {
    func viewForZooming(in scrollView: UIScrollView) -> UIView? {
        tiledView
    }
}
