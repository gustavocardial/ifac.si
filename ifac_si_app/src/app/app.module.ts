import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuAppComponent } from './component/menu-app/menu-app.component';
import { LoginComponent } from './component/login/login.component';
import { FormsModule } from '@angular/forms';
import { FiltersComponent } from './component/filters/filters.component';
import { PostsAppComponent } from './component/posts-app/posts-app.component';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AddNewPostComponent } from './component/add-new-post/add-new-post.component';
import { QuillModule } from 'ngx-quill';
import { DeleteFormComponent } from './component/delete-form/delete-form.component';
import { LoaderComponent } from './component/loader/loader.component';

@NgModule({
  declarations: [
    AppComponent,
    MenuAppComponent,
    LoginComponent,
    FiltersComponent,
    PostsAppComponent,
    AddNewPostComponent,
    DeleteFormComponent,
    LoaderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    CommonModule,
    QuillModule.forRoot() 
  ],
  providers: [
    provideClientHydration()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
