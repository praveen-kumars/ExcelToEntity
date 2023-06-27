import { Component, OnInit } from '@angular/core';
import * as XLSX from 'xlsx';
import { NgForm } from '@angular/forms';
import { DataUploadService } from './data-upload.service';
import { MatRadioChange } from '@angular/material/radio';

@Component({
  selector: 'app-data-upload',
  templateUrl: './data-upload.component.html',
  styleUrls: ['./data-upload.component.css'],
})
export class DataUploadComponent implements OnInit {
  db: any;

  customValue: String;

  selectedOption: String;

  selectedValue: String;

  radioOption: string = 'mongoDb';

  constructor(private dataservice: DataUploadService) {}

  ngOnInit(): void {
    this.dataservice.getMongoLists().subscribe((resp) => {
      this.db = resp;
    });
  }
  onCustomValueChange(newValue: String) {
    if (this.selectedOption === 'other') {
    }
  }

  file: File = null;

  onFilechange(event: any) {
    console.log(event.target.files[0]);
    this.file = event.target.files[0];
  }
  onDropdownchange(value: String) {
    if (value === 'other') {
      this.selectedValue = this.customValue;
    } else {
      this.selectedValue = value;
    }
  }

  upload(form: NgForm) {
    if (this.file) {
      console.log(this.selectedOption);
      if (this.selectedOption === 'other') {
        this.selectedValue = this.customValue;
      } else {
        this.selectedValue = this.selectedOption;
      }
      console.log(this.selectedValue);
      console.log(this.radioOption);
      this.dataservice
        .uploadfile(this.radioOption, this.file, this.selectedValue)
        .subscribe((resp) => {
          alert('Uploaded');
          this.selectedOption = null;
          console.log(this.selectedOption);
          this.selectedValue = null;
          form.reset();
        });
    } else {
      alert('Please select a file first');
    }
  }

  changeOption(e) {
    console.log(e.target.value);
    this.radioOption = e.target.value;
    this.dataservice.getDbLists(this.radioOption).subscribe((resp) => {
      this.db = resp;
      console.log(resp);
    });
  }

  onChange(mrChange: MatRadioChange) {
    console.log(mrChange.value);

    this.radioOption = mrChange.value;
    this.dataservice.getDbLists(this.radioOption).subscribe((resp) => {
      this.db = resp;
      console.log(resp);
    });
  }
}
