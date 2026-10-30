import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';


@Pipe({ name: 'sanitize' })
export class URLSatitizerPipe implements PipeTransform {

    constructor(private _domSanitizer: DomSanitizer) { }

    transform(url) {
        return this._domSanitizer.bypassSecurityTrustResourceUrl(url);
    }
}