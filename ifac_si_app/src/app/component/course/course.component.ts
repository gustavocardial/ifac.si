import { Component } from '@angular/core';
import { CorpoDocente } from '../../model/corpoDocente';

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrl: './course.component.css'
})
export class CourseComponent {
  docentes: CorpoDocente[] = [
    {
      nome: 'Gustavo Cardial',
      nivel: 'Mestre',
      cargo: 'Coordenador e professor'
    },
    {
      nome: 'Flávio Miranda',
      nivel: 'Mestre e doutor',
      cargo: 'Professor'
    },
    {
      nome: 'Marlon',
      nivel: 'Mestre e doutor',
      cargo: 'Professor'
    },
    {
      nome: 'Darueck',
      nivel: 'Mestre',
      cargo: 'Professor'
    },
    {
      nome: 'Silvana',
      nivel: 'Mestre',
      cargo: 'Professora'
    },
    {
      nome: 'Breno Silveira',
      nivel: 'Mestre',
      cargo: 'Professor'
    }
    // {
    //   nome: 'Marlon '
    // }
  ]


}
