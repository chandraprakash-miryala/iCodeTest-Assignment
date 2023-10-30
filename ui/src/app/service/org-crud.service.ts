import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Organization } from '../model/organization';
import { Observable } from 'rxjs/internal/Observable';
import { catchError, shareReplay } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrgCrudService {
  serviceUrl: string;
  constructor(private http: HttpClient) {
    // this.serviceUrl = "http://localhost:8080/orgs"; //dev
    this.serviceUrl = window.location.origin + "/orgs"; //prod
  }

  // private handleError(error: HttpErrorResponse) {
  //   console.log(error.error['message']);
  //   return throwError(() => new Error('Something bad happened; please try again later.'));
  // }

  getOrganizationFields(orgId: string): Observable<string[]> {
    return this.http.get<string[]>(this.serviceUrl+"/"+orgId+"/fields").pipe(shareReplay(1));
  }

  getOrganizationData(orgId: string): Observable<string[][]> {
    return this.http.get<string[][]>(this.serviceUrl+"/"+orgId+"/data").pipe(shareReplay(1));
  }

  getAllOrganizations(): Observable<Organization[]> {
    return this.http.get<Organization[]>(this.serviceUrl).pipe(shareReplay(1));
  }

  addOrganization(org: any): Observable<Organization> {
    return this.http.post<Organization>(this.serviceUrl, org).pipe(shareReplay(1));
  }

  addOrgRowsData(orgId: string, dataRows: string[][]): Observable<number[]> {
    return this.http.post<number[]>(this.serviceUrl+"/"+orgId+"/data", dataRows).pipe(shareReplay(1));
  }

  deleteOrganization(org: Organization): Observable<Organization> {
    return this.http.delete<Organization>(this.serviceUrl+"/"+org.id).pipe(shareReplay(1));
  }
}
