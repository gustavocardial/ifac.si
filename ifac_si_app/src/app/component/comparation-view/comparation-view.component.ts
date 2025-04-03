import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-comparation-view',
  templateUrl: './comparation-view.component.html',
  styleUrl: './comparation-view.component.css'
})
export class ComparationViewComponent implements OnInit{
  constructor ( 
    private router: Router,
    private route: ActivatedRoute,
  ) {}
  
  ngOnInit(): void {
    const id = this.route.snapshot.queryParamMap.get('postId');

    if (id) {
      alert(`Post editado com id: ${id}`)
    }
  }

}
