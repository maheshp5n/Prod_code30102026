import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchfilt'
})
export class SearchfiltPipe implements PipeTransform {

  transform(value: any[], args: string): any[] {
    if(!value){
    return [];
    }
    if(!args){
    return value;
    }
    
    let tempValues :any [];
    tempValues=[];
    value.forEach((singleValue)=>{
    if(typeof (singleValue) === 'object'){
    const s = JSON.stringify(singleValue);
    JSON.parse(s,(_k,v) => {
    if((''+v).toLowerCase().includes(args.toLowerCase())){
    let exists= false;
    let tempValueLength=tempValues.length;
    for(let i=0;i<tempValueLength;i++){
    if(tempValues[i]==singleValue){
    exists =true;
    break;
    }
    }
    if(!exists){
    tempValues.push(singleValue);
    }
    }
    });
    }
    });
    return tempValues;
    }
}
