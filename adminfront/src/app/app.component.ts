import { Component } from '@angular/core';
import { OwlOptions } from 'ngx-owl-carousel-o';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'adminfront';

  customOptions: OwlOptions = {
    loop: true,
    dots: true,
    responsive: {
      0: {
        items: 1
      }
    },
    nav: true
}
}
