import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MAgentsSharedModule } from 'app/shared/shared.module';
import { MAgentsCoreModule } from 'app/core/core.module';
import { MAgentsAppRoutingModule } from './app-routing.module';
import { MAgentsHomeModule } from './home/home.module';
import { MAgentsEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MAgentsSharedModule,
    MAgentsCoreModule,
    MAgentsHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MAgentsEntityModule,
    MAgentsAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class MAgentsAppModule {}
