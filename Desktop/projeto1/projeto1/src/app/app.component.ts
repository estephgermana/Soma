import { Component } from '@angular/core';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss'],
})
export class AppComponent {
  constructor(private alertController: AlertController) {}

  async exibirAlerta() {
    const alert = await this.alertController.create({
      header: 'Alerta',
      message: 'Nome: Estephani Germana Pereira da Silva - Matrícula: 01615970',
      buttons: ['OK']
    });

    await alert.present();
  }
}
