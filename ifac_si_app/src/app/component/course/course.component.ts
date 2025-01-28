import { Component } from '@angular/core';
import { CorpoDocente } from '../../model/corpoDocente';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrl: './course.component.css'
})
export class CourseComponent {
  private docentes: CorpoDocente[] = [
    {
      nome: 'Gustavo Cardial',
      nivel: ['Mestrado'],
      cargo: 'Coordenador'
    },
    // {
    //   nome: 'Marlon '
    // }
  ]


}
