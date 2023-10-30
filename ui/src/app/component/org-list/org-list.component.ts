import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Organization } from 'src/app/model/organization';
import { OrgCrudService } from 'src/app/service/org-crud.service';

@Component({
  selector: 'app-org-list',
  templateUrl: './org-list.component.html',
  styleUrls: ['./org-list.component.scss']
})
export class OrgListComponent implements OnInit {

  organizations: Organization[] = [];
  isLoading: boolean = true;

  @Output() showOrgDetails = new EventEmitter<Organization>();

  constructor(private orgService: OrgCrudService) { }

  ngOnInit(): void {
    this.getAllOrganizations();
  }

  getAllOrganizations() {
    this.orgService.getAllOrganizations().subscribe(res => {
      res.sort((org1, org2) => {
        return org1.name.localeCompare(org2.name);
      });
      this.organizations = res;
      this.isLoading = false;
    }, err => {
      // alert(err);
    });
  }
  
  showDetails(org: Organization) {
    this.showOrgDetails.emit(org);
  }

  delete(org: Organization) {
    this.isLoading = true;
    this.orgService.deleteOrganization(org).subscribe(res => {
      this.getAllOrganizations();
    }, err => {
      // alert(err);
    });
  }

}
