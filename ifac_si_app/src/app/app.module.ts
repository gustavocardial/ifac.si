import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuAppComponent } from './component/menu-app/menu-app.component';
import { LoginComponent } from './component/login/login.component';
import { FormsModule } from '@angular/forms';
import { FiltersComponent } from './component/filters/filters.component';
import { PostsAppComponent } from './component/posts-app/posts-app.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { AddNewPostComponent } from './component/add-new-post/add-new-post.component';
import { QuillModule } from 'ngx-quill';
import { DeleteFormComponent } from './component/delete-form/delete-form.component';
import { LoaderComponent } from './component/loader/loader.component';
import { loaderInterceptor } from './interceptor/loader.interceptor';
import { NotificationComponent } from './component/notification/notification.component';
import { AlertaComponent } from './component/alerta/alerta.component';
import { erroInterceptor } from './interceptor/erro.interceptor';
import { AdminViewComponent } from './component/admin-view/admin-view.component';
import { UserFormComponent } from './component/user-form/user-form.component';
import { CourseComponent } from './component/course/course.component';
import { ViewPostComponent } from './component/view-post/view-post.component';
import { PaginationHandleComponent } from './component/pagination-handle/pagination-handle.component';
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
    NotificationComponent,
    AlertaComponent,
    AdminViewComponent,
    UserFormComponent,
    CourseComponent,
    ViewPostComponent,
    PaginationHandleComponent,
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
    { provide: HTTP_INTERCEPTORS, useClass: loaderInterceptor, multi: true},
    { provide: HTTP_INTERCEPTORS, useClass: erroInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
