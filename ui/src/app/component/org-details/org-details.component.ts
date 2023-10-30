import { Component, Input, OnInit } from '@angular/core';
import { OrgCrudService } from 'src/app/service/org-crud.service';

import * as XLSX from 'xlsx';

@Component({
  selector: 'app-org-details',
  templateUrl: './org-details.component.html',
  styleUrls: ['./org-details.component.scss']
})
export class OrgDetailsComponent implements OnInit {

  isFieldsLoading: boolean = true;
  isDataLoading: boolean = true;
  fieldsNamesList: string[] = [];
  orgDataList: string[][] = [];

  @Input() orgId!: string;
  @Input() orgName!: string;

  constructor(private orgService: OrgCrudService) { }

  ngOnInit(): void {
    this.getOrganizationFields();
  }

  getOrganizationFields() {
    this.orgService.getOrganizationFields(this.orgId).subscribe(res => {
      this.fieldsNamesList = res;
      this.isDataLoading = true
      this.isFieldsLoading = false;
      this.getOrganizationData();
    }, err => {
      this.isFieldsLoading = false;
      alert(err.error['message']);
    });
  }

  getOrganizationData() {
    this.orgService.getOrganizationData(this.orgId).subscribe(res => {
      this.orgDataList = res;
      this.isDataLoading = false;
    }, err => {
      this.isDataLoading = false;
      alert(err.error['message']);
    });
  }

  addOrgRowsData(dataRows: string[][]) {
    this.orgService.addOrgRowsData(this.orgId, dataRows).subscribe(res => {
      this.getOrganizationData()
    }, err => {
      this.isDataLoading = false;
      alert(err.error['message']);
    })
  }

  onFileUpload(event: any) {
    const target: DataTransfer = <DataTransfer>(event.target);
    let acceptedTypes = ['application/vnd.openxmlformats-officedocument.spreadsheetml.sheet', 'application/vnd.ms-excel']
    if (target.files.length == 1) {
      const file = target.files[0];
      // console.log(file);
      if (!acceptedTypes.includes(file.type)) {
        alert('Only EXCEL Files Allowed!');
      } else {
        const reader: FileReader = new FileReader();
        reader.readAsBinaryString(target.files[0]);
        reader.onload = (e: any) => {
          let data = e.target.result;
          const workbook: XLSX.WorkBook = XLSX.read(data, { type: 'binary' });
          console.log(workbook);
          const worksheetName: string = workbook.SheetNames[0];
          const worksheet1: XLSX.WorkSheet = workbook.Sheets[worksheetName];
          this.isDataLoading = true;
          this.addOrgRowsData((XLSX.utils.sheet_to_json(worksheet1, { header: 1, range: 1 })));
        };
      }
    }
    event.target.value = '';
  }

  onTemplateDownload() {
    if(this.fieldsNamesList.length == 0) {
      alert("Error in Fetching Organization Fields. Please Refresh the Page");
    } else {
      const worksheet: XLSX.WorkSheet = XLSX.utils.aoa_to_sheet([this.fieldsNamesList,[]]);
      const workbook: XLSX.WorkBook = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(workbook, worksheet, "Template");
      XLSX.writeFile(workbook, this.orgName.replace(/\s/g, "_") + "_template.xlsx");
    }      
  }

}
