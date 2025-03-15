import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { PostsAppComponent } from './component/posts-app/posts-app.component';
import { AddNewPostComponent } from './component/add-new-post/add-new-post.component';
import { NotificationComponent } from './component/notification/notification.component';
import { AdminViewComponent } from './component/admin-view/admin-view.component';
import { CourseComponent } from './component/course/course.component';
import { ViewPostComponent } from './component/view-post/view-post.component';
import { AuthGuard } from './service/auth.guard';
import { MyPublicationsComponent } from './component/my-publications/my-publications.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'view_posts', component: PostsAppComponent},
  {path:'course', component: CourseComponent},
  {path:'view_post', component: ViewPostComponent},
  {path: 'administration', canActivate: [AuthGuard], children: [
    {path: 'autor', canActivate: [AuthGuard], data: { cargo: ['AUTOR', 'EDITOR', 'ADMIN'] }, children: [
      {path:'new_post', component: AddNewPostComponent},
    ]},
    {path: 'editor', canActivate: [AuthGuard], data: { cargo: ['EDITOR', 'ADMIN'] }, children: [
      {path: 'notification', component: NotificationComponent},
      {path:'new_post', component: AddNewPostComponent},
    ]},
    {path: 'admin', canActivate: [AuthGuard], data: { cargo: 'ADMIN' }, children: [
      {path: 'viewUsers', component: AdminViewComponent},
    ]},
    {path: 'my_publications', component: MyPublicationsComponent ,canActivate: [AuthGuard], data: { cargo: ['AUTOR', 'EDITOR', 'ADMIN'] }},
  ]},
  {path: '', redirectTo: 'view_posts', pathMatch: 'full'},
  {path: '**', redirectTo: 'view_posts'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
