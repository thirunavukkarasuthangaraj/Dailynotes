import { HttpClient } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LearningEntry } from 'src/app/models/learning-entry.model';
import { LearningService } from 'src/app/services/learning.service';
import { Editor } from 'ngx-editor';

@Component({
  selector: 'app-entry-form',
  templateUrl: './entry-form.component.html',
  styleUrls: ['./entry-form.component.scss']
})
export class EntryFormComponent implements OnDestroy, OnInit{
  entry: LearningEntry = { title: '', description: '', date: new Date().toISOString() };
  selectedFile?: File;
  preview?: string;
  url: string = "http://localhost:8080";
  editor!: Editor;

  constructor(private service: LearningService, private http: HttpClient, private router: Router) {
    const nav = this.router.getCurrentNavigation();
    const state = nav?.extras?.state as { entry: LearningEntry };
    if (state?.entry) {
      this.entry = { ...state.entry };
    }
  }

  ngOnInit() {
    this.editor = new Editor();
  }


  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      const reader = new FileReader();
      reader.onload = () => this.preview = reader.result as string;
      reader.readAsDataURL(file);
    }
  }
    
  ngOnDestroy(): void {
      this.editor.destroy();
    }


  save() {
    if (!this.validateForm()) return;
  
    if (this.selectedFile) {
      this.service.upload(this.selectedFile).subscribe((res: any) => {
        this.entry.imageUrl = res.data; 
        this.saveEntry();  
      }, error => {
        console.error("Image upload failed", error);
      });
    } else {
      this.saveEntry(); // ✅ Save without image
    }
  }
  

  saveEntry() {
    if (this.entry.id) {
      this.service.update(this.entry.id, this.entry).subscribe(() => this.router.navigate(['/']));
    } else {
      this.service.create(this.entry).subscribe(() => this.router.navigate(['/']));
    }
  }

formErrors: string[] = [];
validateForm(): boolean {
  this.formErrors = [];

  if (!this.entry.title || this.entry.title.trim().length < 3) {
    this.formErrors.push("Title must be at least 3 characters long.");
  }

  if (!this.entry.description || this.entry.description.trim().length < 5) {
    this.formErrors.push("Description must be at least 5 characters long.");
  }

  if (!this.entry.date) {
    this.formErrors.push("Date is required.");
  }

  return this.formErrors.length === 0;
}

}
