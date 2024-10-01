import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './component/login/login.component';
import { PostsAppComponent } from './component/posts-app/posts-app.component';

const routes: Routes = [
  {path:'login', component: LoginComponent},
  {path:'view_posts', component: PostsAppComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
