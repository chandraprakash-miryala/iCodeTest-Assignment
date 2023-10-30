import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormControl, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { Organization } from 'src/app/model/organization';
import { OrgCrudService } from 'src/app/service/org-crud.service';

@Component({
  selector: 'app-create-org',
  templateUrl: './create-org.component.html',
  styleUrls: ['./create-org.component.scss']
})
export class CreateOrgComponent implements OnInit {

  orgForm!: FormGroup;
  isLoading: boolean = false;
  org!: Organization;
  submitted: boolean = false;

  @Output() showOrgList = new EventEmitter<any>();

  constructor(private formBuilder: FormBuilder,
    private orgService: OrgCrudService) {  
  }

  ngOnInit(): void {

    this.orgForm = this.formBuilder.group({  
      name: ['', [Validators.required, Validators.maxLength(255)]],  
      fields: this.formBuilder.array([this.formBuilder.group({  
        field: ['', [Validators.required, Validators.maxLength(255)]], 
      })]), 
    });
  }
    
  fields() : FormArray {  
    return this.orgForm.get("fields") as FormArray  
  }  
     
  newField(): FormGroup {  
    return this.formBuilder.group({  
      field: ['', [Validators.required, Validators.maxLength(255)]], 
    });
  }  
     
  addField() {  
    this.fields().push(this.newField());  
  }  
     
  removeField(i:number) {  
    this.fields().removeAt(i);  
  }

  getField(i: number): AbstractControl {
    return ((this.orgForm.controls.fields as FormArray).controls[i] as FormGroup).controls.field;
  }

  addOrganization() {
    if(this.orgForm.status == "INVALID") {
      this.submitted = true;
    } else {
      this.isLoading = true;
      this.org = new Organization("", this.orgForm.controls.name.value);
      this.orgService.addOrganization([this.org, this.orgForm.controls.fields.value]).subscribe(res => {
        this.isLoading = false;
        this.submitted = false;
        this.showOrgList.emit();
      }, err => {
        this.isLoading = false;
        alert(err.error['message']);
      });
    }    
  }

}
