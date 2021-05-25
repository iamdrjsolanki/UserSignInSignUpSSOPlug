import { NgModule } from "@angular/core";
import { Router, RouterModule, Routes } from "@angular/router";
import { AuthGuard } from "./auth/auth.gaurd";
import { CallbackComponent } from "./callback/callback.component";
import { HomepageComponent } from "./homepage/homepage.component";
import { RegisterLoginComponent } from "./register-login/register-login.component";

const appRoutes: Routes = [
    { path: '', redirectTo: '/register-login', pathMatch: 'full' },
    { path: 'register-login', component: RegisterLoginComponent },
    { path: 'homepage', component: HomepageComponent, canActivate: [AuthGuard] },
    { path: 'callback', component: CallbackComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(appRoutes)],
    exports: [RouterModule]
})
export class AppRoutingModule {}