import { URLSatitizerPipe } from './url-safe-pipe.pipe';
import { NgModule } from '@angular/core';

@NgModule({
    declarations: [URLSatitizerPipe],
    exports: [URLSatitizerPipe]
})
export class URLSafeModule {

}
