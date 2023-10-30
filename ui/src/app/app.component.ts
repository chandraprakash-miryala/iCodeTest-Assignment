import { Component, OnInit } from '@angular/core';
import { Organization } from './model/organization';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  createOrg: boolean = false;
  orgList: boolean = false;
  orgDetails: boolean = false;
  selectedOrgId!: string;
  selectedOrgName!: string;

  ngOnInit(): void {
    this.orgList = true;
  }

  addOrg() {
    this.createOrg = true;
    this.orgList = false;
    this.orgDetails = false;
  }

  showOrgList() {
    this.createOrg = false;
    this.orgList = true;
    this.orgDetails = false;
  }

  showOrgDetails(org: Organization) {
    this.selectedOrgId = org.id;
    this.selectedOrgName  = org.name;
    this.createOrg = false;
    this.orgList = false;
    this.orgDetails = true;
  }
}
