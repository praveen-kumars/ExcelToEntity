import { Injectable } from "@angular/core";
import { catchError } from "rxjs";

import { HttpClient, HttpErrorResponse, HttpHandler, HttpHeaders } from '@angular/common/http';



@Injectable({providedIn: 'root'})
export class DataUploadService{

    
    constructor(private httpClient: HttpClient){}

    

    public getDbLists(radioOption:string){

        if(radioOption==="mongoDb"){
        
         return this.httpClient.get('http://localhost:8010/db')}
         else{
            return this.httpClient.get('http://localhost:8011/db')

         }

    }


    public uploadfile(radioOption:string,file: File,dbName:String) {
        let formParams = new FormData();
        formParams.append('file', file)
        if(radioOption==="mongoDb"){
        return this.httpClient.post(`http://localhost:8010/UploadExcelSheet/${dbName}`, formParams)}
        else{
            return this.httpClient.post(`http://localhost:8011/UploadExcelSheet/${dbName}`, formParams)

        }
      }

      public getMongoLists(){
         return this.httpClient.get('http://localhost:8010/db')
         

    }

      
}
