import { Directive, ElementRef, Host, HostListener } from '@angular/core';

@Directive({
  selector: '[appDragable]',
  exportAs: 'appDragable'
})
export class DragableDirective {
  pos1 = 0;
  pos2 = 0;
  pos3 = 0;
  pos4 = 0;

  constructor(private elRef:ElementRef) {
    this.elRef.nativeElement.onmousedown=this.dragMouseDown;
  }

  dragMouseDown(element:any){
    element = element || window.event;
    element.preventDefault();
    // get the mouse cursor position at startup:
    this.pos3 = element.clientX;
    this.pos4 = element.clientY;
    document.onmouseup = this.closeDragElement;
    // call a function whenever the cursor moves:
    document.onmousemove = this.elementDrag;
  }

  elementDrag(e) {
    e = e || window.event;
    e.preventDefault();
    // calculate the new cursor position:
    this.pos1 = this.pos3 - e.clientX;
    this.pos2 = this.pos4 - e.clientY;
    this.pos3 = e.clientX;
    this.pos4 = e.clientY;
    // set the element's new position:
    this.elRef.nativeElement.style.top = (this.elRef.nativeElement.offsetTop - this.pos2) + "px";
    this.elRef.nativeElement.style.left = (this.elRef.nativeElement.offsetLeft - this.pos1) + "px";
  }

  closeDragElement() {
    /* stop moving when mouse button is released:*/
    document.onmouseup = null;
    document.onmousemove = null;
  }

}
