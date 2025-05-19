import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EntryFormComponent } from './components/entry-form/entry-form.component';
import { EntryListComponent } from './components/entry-list/entry-list.component';

const routes: Routes = [
  { path: '', component: EntryListComponent },
  { path: 'add', component: EntryFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { 

  
  
}
