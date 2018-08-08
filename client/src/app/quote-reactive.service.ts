import { Injectable } from '@angular/core';

import { Quote } from './quote';

import * as EventSource from 'eventsource';
import { Observable } from 'rxjs/Observable';
import { NgZone } from '@angular/core';

@Injectable()
export class QuoteReactiveService {

  quotes: Quote[] = [];
  url: string = 'http://localhost:8080/quotes-reactive';
  urlPaged: string = 'http://localhost:8080/quotes-reactive-paged';

  constructor(private zone: NgZone) {}

  getQuoteStream(page?: number, size?: number): Observable<Array<Quote>> {
    this.quotes = [];
    return Observable.create((observer) => {
      let url = this.url;
      if (page != null) {
        url = this.urlPaged + '?page=' + page + '&size=' + size;
      }
      
      let eventSource = new EventSource(url);
      eventSource.onmessage = (event) => {
        console.debug('Received event: ', event);
        let json = JSON.parse(event.data);
        this.quotes.push(new Quote(json['id'], json['book'], json['content']));
        this.zone.run(() => observer.next(this.quotes));
      };

      eventSource.onerror = (error) => {
        if (eventSource.readyState === 0) {
          console.log('The stream has been closed by the server.');
          eventSource.close();
          observer.complete();
        } else {
          observer.error('EventSource error: ' + error);
        }
      }
    });
  }
}
