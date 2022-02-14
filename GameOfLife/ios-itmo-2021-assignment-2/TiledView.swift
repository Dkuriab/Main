//
//  TiledView.swift
//  ios-itmo-2021-assignment-2
//
//  Created by Danila on 15.01.2022.
//
import UIKit


class TiledView: UIView {
    var selectView: UIView!
    var insertView: UIView!
    var resizeTap = ResizeTap()
    var insertViewMoving = false
    let proxyFactor = CGFloat(10)
    var cellHeight = 0
    var cellWidth = 0
    var boundSize = CGFloat(2.5)
    public weak var curState: TiledViewState?
    
    override class var layerClass: AnyClass { CATiledLayer.self }
    
    var tiledLayer: CATiledLayer { layer as! CATiledLayer }

    
    override var contentScaleFactor: CGFloat {
        didSet { super.contentScaleFactor = 1 }
    }
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        cellWidth = Int(tiledLayer.tileSize.width)
        cellHeight = Int(tiledLayer.tileSize.height)
        
        selectView = UIView(frame: CGRect(x: 0, y: 0, width: cellWidth * 2, height: cellHeight * 2))
        selectView.backgroundColor = UIColor.systemBlue.withAlphaComponent(0.2)
        selectView.layer.cornerRadius = 10
        selectView.isHidden = true
        
        insertView = UIView(frame: CGRect(x: 0, y: 0, width: cellWidth * 2, height: cellHeight * 2))
        insertView.backgroundColor = UIColor.white.withAlphaComponent(0.5)
        insertView.layer.borderWidth = 2
        insertView.layer.borderColor = UIColor.systemGray.cgColor
        insertView.layer.cornerRadius = 10
        insertView.isHidden = true
        
        
        addSubview(selectView)
        addSubview(insertView)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    override func draw(_ rect: CGRect) {
        guard let context = UIGraphicsGetCurrentContext() else { fatalError("Error loading context") }
        
        cellWidth = Int(tiledLayer.tileSize.width)
        cellHeight = Int(tiledLayer.tileSize.height)
        
        context.setFillColor(UIColor.white.withAlphaComponent(alpha).cgColor)
        context.fill(rect)

        let x = Int(rect.origin.x) / cellWidth
        let y = Int(rect.origin.y) / cellHeight

        context.setFillColor(UIColor.systemGray5.withAlphaComponent(alpha).cgColor)
        
        if (curState != nil) {
            switch (curState!.automataState[Point(x: x, y: y)]) {
            case .inactive:
                context.setFillColor(UIColor.systemGray5.withAlphaComponent(alpha).cgColor)
                break
            case .active:
                context.setFillColor(UIColor.systemGray.withAlphaComponent(alpha).cgColor)
                break
            }
        }
        
        boundSize = CGFloat(cellWidth) / 20
        let drawingRect = rect.inset(by: UIEdgeInsets(top: boundSize, left: boundSize, bottom: boundSize, right: boundSize))
        let clipPath: CGPath = UIBezierPath(roundedRect: drawingRect, cornerRadius: CGFloat(0.1 * Double(cellWidth))).cgPath
        context.addPath(clipPath)
        context.fillPath()
    }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
            let touchStart = touch.location(in: self)
            var isChanging = false
            insertViewMoving = false
            
            resizeTap = ResizeTap()
            
            if      touchStart.y > insertView.frame.minY
                    &&  touchStart.y < insertView.frame.maxY
                    &&  touchStart.x > insertView.frame.minX
                    &&  touchStart.x < insertView.frame.maxX {
                insertViewMoving = true
                isChanging = true
            }
            
            if  touchStart.y > selectView.frame.minY + (proxyFactor * 2) &&  touchStart.y < selectView.frame.maxY - (proxyFactor * 2) &&  touchStart.x > selectView.frame.minX + (proxyFactor * 2) &&  touchStart.x < selectView.frame.maxX - (proxyFactor * 2) {
                resizeTap.middelTouch = true
                isChanging = true
            }
            
            if touchStart.y > selectView.frame.maxY - proxyFactor &&  touchStart.y < selectView.frame.maxY + proxyFactor {
                resizeTap.bottomTouch = true
                isChanging = true
            }
            
            if touchStart.x > selectView.frame.maxX - proxyFactor && touchStart.x < selectView.frame.maxX + proxyFactor {
                resizeTap.rightTouch = true
                isChanging = true
            }
            
            if touchStart.x > selectView.frame.minX - proxyFactor &&  touchStart.x < selectView.frame.minX + proxyFactor {
                resizeTap.leftTouch = true
                isChanging = true
            }
            
            if touchStart.y > selectView.frame.minY - proxyFactor &&  touchStart.y < selectView.frame.minY + proxyFactor {
                resizeTap.topTouch = true
                isChanging = true
            }
            
            if isChanging {
                curState!.scrollView.isScrollEnabled = false
            }
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
            let currentTouchPoint = touch.location(in: self)
            let previousTouchPoint = touch.previousLocation(in: self)
            
            let deltaX = currentTouchPoint.x - previousTouchPoint.x
            let deltaY = currentTouchPoint.y - previousTouchPoint.y
            var newOrigin = selectView.frame.origin
            var newSize = selectView.frame.size
            var newInsertViewOrigin = insertView.frame.origin
            
            if insertViewMoving {
                newInsertViewOrigin = CGPoint(x: newInsertViewOrigin.x + deltaX, y: newInsertViewOrigin.y + deltaY)
            }
            
            if resizeTap.middelTouch{
                newOrigin = CGPoint(x: newOrigin.x + deltaX, y: newOrigin.y + deltaY)
            }
            
            if resizeTap.topTouch {
                newOrigin = CGPoint(x: newOrigin.x, y: newOrigin.y + deltaY)
                newSize = CGSize(width: newSize.width, height: newSize.height - deltaY)
            }
            
            if resizeTap.leftTouch {
                newOrigin = CGPoint(x: newOrigin.x + deltaX, y: newOrigin.y)
                newSize = CGSize(width: newSize.width - deltaX, height: newSize.height)
            }
            if resizeTap.rightTouch {
                newSize = CGSize(width: newSize.width + deltaX, height: newSize.height)
            }
            if resizeTap.bottomTouch {
                newSize = CGSize(width: newSize.width, height: newSize.height + deltaY)
            }
            
            selectView.frame = CGRect(origin: newOrigin, size: newSize)
            insertView.frame = CGRect(origin: newInsertViewOrigin, size: insertView.frame.size)
            
            UIView.animate(withDuration: 0.25, delay: 0, options: UIView.AnimationOptions.curveEaseIn, animations: {
                self.selectView.layoutIfNeeded()
                self.insertView.layoutIfNeeded()
            }, completion: { (ended) in
                
            })
        }
    }
    
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
        touchesEnded(touches, with: event)
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        cellWidth = Int(tiledLayer.tileSize.width)
        cellHeight = Int(tiledLayer.tileSize.height)
        
        if (!curState!.scrollView.isScrollEnabled) {
            insertViewMoving = false
            
            let insertViewOrigin = insertView.frame.origin
            var newX = (Int(insertViewOrigin.x + 5) / cellWidth) * cellWidth
            var newY = (Int(insertViewOrigin.y + 5) / cellHeight) * cellHeight
            
            newX = abs(CGFloat(newX) - (insertViewOrigin.x + 5)) > CGFloat(cellWidth) / 2 ? newX + cellWidth : newX
            newY = abs(CGFloat(newY) - (insertViewOrigin.y + 5)) > CGFloat(cellHeight) / 2 ? newY + cellHeight : newY
            
            let newInsertViewOrigin = CGPoint(x: newX - 5, y: newY - 5)
            insertView.frame = CGRect(origin: newInsertViewOrigin, size: insertView.frame.size)
            insertView.layoutIfNeeded()
            
            curState!.scrollView.isScrollEnabled = true
            let origin = selectView.frame.origin
            let size = selectView.frame.size
            
            newX = (Int(origin.x) / cellWidth) * cellWidth
            newY = (Int(origin.y) / cellHeight) * cellHeight
            
            newX = abs(CGFloat(newX) - origin.x) > CGFloat(cellWidth) / 2 ? newX + cellWidth : newX
            newY = abs(CGFloat(newY) - origin.y) > CGFloat(cellHeight) / 2 ? newY + cellHeight : newY
            
            let newOrigin = CGPoint(x: newX, y: newY)
            let newSize = CGSize(width: (Int(size.width) / cellWidth) * cellWidth, height: (Int(size.height) / cellHeight) * cellHeight)
            selectView.frame = CGRect(origin: newOrigin, size: newSize)
            selectView.layoutIfNeeded()
        }
    }
}
