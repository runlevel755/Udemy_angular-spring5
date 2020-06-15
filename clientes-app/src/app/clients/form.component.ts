import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from './client.service';
import { Router, ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html'
})
export class FormComponent implements OnInit {

private client: Client = new Client()
private title: string = "Create client"

  constructor(private clientService: ClientService,
    private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.uploadClient()
  }

  uploadClient(): void {
    this.activatedRoute.params.subscribe(params =>{
      let id = params['id']
      if(id){
        this.clientService.getClient(id).subscribe((client) => this.client = client)
      }
    })
  }

  public create(): void{
    this.clientService.create(this.client)
    .subscribe(response => {
        this.router.navigate(['/clients'])
        swal.fire('New Client', `Client ${response.name} created successfully!`, 'success')
      }
    );
  }

  update(): void {
    this.clientService.update(this.client)
    .subscribe( client =>{
      this.router.navigate(['/clients'])
      swal.fire('Client Update', `Client ${client.name} updated successfully!`, 'success')
    }

    )
  }

}
