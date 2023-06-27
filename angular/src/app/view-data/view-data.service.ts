import { Injectable } from '@angular/core';
import { catchError } from 'rxjs';

import {
  HttpClient,
  HttpErrorResponse,
  HttpHandler,
  HttpHeaders,
} from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ViewDataService {
  constructor(private httpClient: HttpClient) {}

  public getDbLists(radioOption: string) {
    if (radioOption === 'mongoDb') {
      return this.httpClient.get('http://localhost:8010/db');
    } else {
      return this.httpClient.get('http://localhost:8011/db');
    }
  }

  public getDataFromDb(dbName, radioOption: string) {
    if (radioOption === 'mongoDb') {
      return this.httpClient.get(`http://localhost:8010/all/${dbName}`);
    } else {
      return this.httpClient.get(`http://localhost:8011/all/${dbName}`);
    }
  }

  public getMongoLists() {
    return this.httpClient.get('http://localhost:8010/db');
  }
}
