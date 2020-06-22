document.body.onmouseover = document.body.onmouseout = handler;


function handler(event) {
    if (event.target.className ==='cell' && event.type === 'mouseover' ) {
        event.target.style.background = "rgba(147,147,147,0.37)";
    }
    if (event.type === 'mouseout') {
        event.target.style.background= ''
    }
}