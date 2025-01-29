import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { PostsAppComponent } from './component/posts-app/posts-app.component';
import { AddNewPostComponent } from './component/add-new-post/add-new-post.component';
import { NotificationComponent } from './component/notification/notification.component';
import { AdminViewComponent } from './component/admin-view/admin-view.component';
import { CourseComponent } from './component/course/course.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'view_posts', component: PostsAppComponent},
  {path:'course', component: CourseComponent},
  {path: 'administration', children: [
    {path: 'autor', children: [
      {path:'new_post', component: AddNewPostComponent},
    ]},
    {path: 'editor', children: [
      {path: 'notification', component: NotificationComponent},
    ]},
    {path: 'admin', children: [
      {path: 'viewUsers', component: AdminViewComponent},
    ]},
    // {path: 'my_publications',},
  ]},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
