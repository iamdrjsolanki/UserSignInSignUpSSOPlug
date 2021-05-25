import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserSsoAuthenticationService } from '../services/user-sso-authentication.service';

@Component({
  selector: 'app-callback',
  templateUrl: './callback.component.html',
  styleUrls: ['./callback.component.css']
})
export class CallbackComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private userSsoAuthService: UserSsoAuthenticationService
  ) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe( p => {
      this.userSsoAuthService.fetchToken(p.code, p.state).subscribe( data => {
        this.userSsoAuthService.updateToken(data.accessToken);
        this.router.navigate(['/homepage']);
      } )
    } );
  }

}
