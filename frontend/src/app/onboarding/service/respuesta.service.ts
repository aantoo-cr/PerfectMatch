import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RespuestaGuardar } from '../domain/dto';

@Injectable({ providedIn: 'root' })
export class RespuestaService {
  private readonly baseUrl = '/api/onboarding/respuestas';

  constructor(private readonly http: HttpClient) {}

  guardar(body: RespuestaGuardar): Observable<void> {
    return this.http.post<void>(this.baseUrl, body);
  }
}
