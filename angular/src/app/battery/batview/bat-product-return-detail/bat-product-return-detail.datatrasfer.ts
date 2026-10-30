import { Injectable } from '@angular/core';
import { BtProductReturnResponse } from '../../bt_model';

@Injectable()
export class BtProductreturnorderDetailsDataTransfer{
    public btProductReturnRes:BtProductReturnResponse;
    public constructor(){}
}