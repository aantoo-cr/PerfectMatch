import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UbicacionResp } from '../domain/dto';

@Injectable({ providedIn: 'root' })
export class UbicacionService {
  private readonly baseUrl = '/api/onboarding/ubicaciones';

  constructor(private readonly http: HttpClient) {}

  listar(): Observable<UbicacionResp[]> {
    return this.http.get<UbicacionResp[]>(this.baseUrl);
  }
}
