import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Editor } from 'ngx-editor';
import { LearningEntry } from 'src/app/models/learning-entry.model';
import { LearningService } from 'src/app/services/learning.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-entry-list',
  templateUrl: './entry-list.component.html',
  styleUrls: ['./entry-list.component.scss']
})

export class EntryListComponent implements OnInit, OnDestroy {
  entries: LearningEntry[] = [];
  filterTag = '';
  selectedFile?: File;
  preview?: string;
  constructor(private service: LearningService, private router: Router, private sanitizer: DomSanitizer) { }
  url: string = "http://localhost:8080";
  selectedTag: string = '';
  availableTags: string[] = [];
  editor!: Editor; 

  getSanitizedHtml(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }

  ngOnInit(): void {
    this.editor = new Editor();
    this.service.getAll().subscribe((res: any) => this.entries = res.data);
  }

  ngOnDestroy(): void {
    this.editor.destroy();
  }


  delete(id: any) {
    if (confirm('Are you sure to delete this entry?')) {
      this.service.delete(id).subscribe(() => {
        this.entries = this.entries.filter(e => e.id !== id);
      });
    }
  }

  edit(entry: LearningEntry) {
    this.router.navigate(['/add'], { state: { entry } });
  }

  filteredEntries(): LearningEntry[] {
    if (!this.filterTag.trim()) return this.entries;

    const filter = this.filterTag.toLowerCase();
    return this.entries.filter(e =>
      e.tags?.toLowerCase().includes(filter) ||
      e.title?.toLowerCase().includes(filter) ||
      e.description?.toLowerCase().includes(filter)
    );
  }
}
