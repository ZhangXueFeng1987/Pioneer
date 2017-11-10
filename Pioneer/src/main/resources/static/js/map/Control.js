function HomeControl(){  
    // 设置默认停靠位置和偏移量  
    this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;  
    this.defaultOffset = new BMap.Size(10, 10);
}
HomeControl.prototype = new BMap.Control();
HomeControl.prototype.initialize = function(map){  
	// 创建一个DOM元素  
    var div = document.createElement("div");  
    // 添加文字说明  
    $(div).html('<a class="nav-link" id="mapHomeId" href="../index" style="padding:0xp;">首页</a>')
    // 设置样式  
    div.style.cursor = "pointer";  
    // 添加DOM元素到地图中  
    map.getContainer().appendChild(div);  
    // 将DOM元素返回  
    return div;  
}  


function ImagesPreViewControl(){  
    // 设置默认停靠位置和偏移量  
    this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;  
    this.defaultOffset = new BMap.Size(80, 80);
}
ImagesPreViewControl.prototype = new BMap.Control();
ImagesPreViewControl.prototype.initialize = function(map){  
	// 创建一个DOM元素  
    var div = document.createElement("div");  
    // 添加文字说明  
    $(div).html('<span id="ImagesPreViewControl"></span>');
    // 设置样式  
    div.style.cursor = "pointer";
    // 添加DOM元素到地图中  
    map.getContainer().appendChild(div);  
    // 将DOM元素返回  
    return div;  
} 