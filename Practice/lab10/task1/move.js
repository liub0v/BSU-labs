document.body.onmouseover = document.body.onmouseout = handler;


function handler(event) {
    if (event.target.className ==='circle' && event.type === 'mouseover' ) {
        event.target.style.background = 'LightCoral'
    }
    if (event.type === 'mouseout') {
        event.target.style.background= ''
    }
}