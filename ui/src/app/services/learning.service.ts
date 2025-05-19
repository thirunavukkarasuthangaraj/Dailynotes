import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LearningEntry } from '../models/learning-entry.model';

@Injectable({ providedIn: 'root' })
export class LearningService {
  private static api = 'http://localhost:8081/api/v1/learn';
  constructor(private http: HttpClient) {}

  getAll() { return this.http.get<LearningEntry[]>(LearningService.api); }
  get(id: string) { return this.http.get<LearningEntry>(`${LearningService.api}/${id}`); }
  create(e: LearningEntry) { return this.http.post(LearningService.api, e); }
  update(id: string, e: LearningEntry) { return this.http.put(`${LearningService.api}/${id}`, e); }
  delete(id: string) { return this.http.delete(`${LearningService.api}/${id}`); }

  upload(file: File) {
    const formData = new FormData();
    formData.append("file", file);
    return this.http.post<string>(`${LearningService.api}/upload`, formData);
  }
}
