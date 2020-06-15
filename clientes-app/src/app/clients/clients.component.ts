import { Component, OnInit } from '@angular/core';
import { Client } from './client';
import { ClientService } from './client.service';
import swal from 'sweetalert2';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html'
})
export class ClientsComponent implements OnInit {

  clients: Client[];

  constructor(private ClientService: ClientService) { }

  ngOnInit() {
    this.ClientService.getClients().subscribe(
      clients => this.clients = clients
    );
  }

  delete(client: Client): void{
    const swalWithBootstrapButtons = swal.mixin({
  customClass: {
    confirmButton: 'btn btn-success',
    cancelButton: 'btn btn-danger'
  },
  buttonsStyling: false
})

swalWithBootstrapButtons.fire({
  title: 'Are you sure?',
  text: "You won't be able to revert this!",
  icon: 'warning',
  showCancelButton: true,
  confirmButtonText: 'Yes, delete it!',
  cancelButtonText: 'No, cancel!',
  reverseButtons: true
}).then((result) => {
  if (result.value) {
    this.ClientService.delete(client.id).subscribe(
      response => {
        this.clients = this.clients.filter(cli => cli !== client)
        swal.fire(
          'Client deleted',
          `Client ${client.name} deleted successfully.`,
          'success'
        )
      }
    )

    swalWithBootstrapButtons.fire(
      'Deleted!',
      'Your file has been deleted.',
      'success'
    )
  }
})
  }
}
