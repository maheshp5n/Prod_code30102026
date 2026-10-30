import { NgModule } from "@angular/core";
import { DragableDirective } from "./dragable.directive";

@NgModule({
    declarations:[DragableDirective],
    exports:[DragableDirective]
})
export class DragableModule{}