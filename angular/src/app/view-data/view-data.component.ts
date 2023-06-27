import { Component, OnDestroy, OnInit } from '@angular/core';
import { ViewDataService } from './view-data.service';
import { Subject } from 'rxjs';
import { MatRadioChange } from '@angular/material/radio';

@Component({
  selector: 'app-view-data',
  templateUrl: './view-data.component.html',
  styleUrls: ['./view-data.component.css'],
})
export class ViewDataComponent implements OnInit {
  db: any;
  radioOption: string = 'mongoDb';

  DbData: any;
  keys: any;

  selectedOption: String;

  constructor(private viewData: ViewDataService) {}

  ngOnInit(): void {
    this.viewData.getMongoLists().subscribe((resp) => {
      this.db = resp;
    });
  }

  onClickSubmit() {
    console.log(this.selectedOption);
    console.log(this.radioOption);
    this.viewData
      .getDataFromDb(this.selectedOption, this.radioOption)
      .subscribe((response) => {
        this.DbData = response;
        this.keys = this.DbData[0];
        console.log(this.DbData);
      });
  }

  getkeys(): String[] {
    let keys: String[] = [];
    this.DbData.array.forEach((items) => {
      Object.keys(items).forEach((key) => {
        if (!keys.includes(key)) {
          keys.push(key);
        }
      });
    });
    return keys;
  }

  changeOption(e) {
    console.log(e.target.value);
    this.radioOption = e.target.value;
    this.viewData.getDbLists(this.radioOption).subscribe((resp) => {
      this.db = resp;
      console.log(resp);
    });
  }

  onChange(mrChange: MatRadioChange) {
    console.log(mrChange.value);

    this.radioOption = mrChange.value;
    this.viewData.getDbLists(this.radioOption).subscribe((resp) => {
      this.db = resp;
      console.log(resp);
    });
  }
}
