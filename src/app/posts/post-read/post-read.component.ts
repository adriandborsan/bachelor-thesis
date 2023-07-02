import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Input, OnInit, QueryList, ViewChild, ViewChildren, ViewContainerRef } from '@angular/core';
import { Post, PostResponse } from '../post.model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-read',
  templateUrl: './post-read.component.html',
  styleUrls: ['./post-read.component.scss']
})
export class PostReadComponent implements OnInit {
  @Input() userId: string | null = null;
  @ViewChildren('sentinel', { read: ViewContainerRef }) sentinel!: QueryList<ViewContainerRef>;
  posts: Post[] = [];
  page: number = 0;
  loading: boolean = false;
  observer!: IntersectionObserver;
  noMorePosts: boolean = false;

  constructor(private postService: PostService, private cdRef: ChangeDetectorRef) {
    this.postService.postsModifiedEventEmitter.subscribe(() => this.refreshData());
  }

  ngOnInit(): void {
    this.refreshData();
  }

  ngAfterViewInit(): void {
    this.sentinel.changes.subscribe(() => {
      if (this.sentinel.first) {
        this.initializeObserver();
      }
    });

    // If the sentinel is already present, initialize the observer
    if (this.sentinel.first) {
      this.initializeObserver();
    }
  }

  initializeObserver(): void {
    setTimeout(() => {
      if (this.sentinel.first) {
        // //console.log(this.sentinel.first.element.nativeElement); // debugging

        this.observer = new IntersectionObserver((entries) => {
          if (entries[0].isIntersecting && !this.loading&& !this.noMorePosts) {
            this.loadMoreData();
          }
        });
        this.observer.observe(this.sentinel.first.element.nativeElement);
      }
    }, 1000);
  }



  refreshData(): void {
    this.page = 0; // reset the page number
    this.noMorePosts = false;

    const posts$ = this.userId
      ? this.postService.getPostsByUser(this.userId, this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}])
      : this.postService.getPosts(this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}]);

    posts$.subscribe(postResponse => {
      this.posts = postResponse.content;
      this.page++;
    });
  }

  async loadMoreData(): Promise<void> {
    this.loading = true;
    const posts$ = this.userId
    ? this.postService.getPostsByUser(this.userId, this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}])
    : this.postService.getPosts(this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}]);
    posts$.subscribe(async postResponse => {
      if (postResponse.content.length === 0) {
        // There are no more posts to load
        this.loading = false;
        this.noMorePosts = true;
        if (this.observer) {
          // this.observer.disconnect(); // stop observing when there are no more posts
        }
      } else {
        this.posts = [...this.posts, ...postResponse.content];
        this.page++;
        this.loading = false;

        // Trigger change detection and wait for the DOM to update
        this.cdRef.detectChanges();
        await new Promise(resolve => setTimeout(resolve, 0));

        // Check if the sentinel is still visible
        const sentinelRect = this.sentinel.first.element.nativeElement.getBoundingClientRect();
        if (sentinelRect.top <= window.innerHeight && sentinelRect.bottom >= 0 && !this.loading) {
          this.loadMoreData();
        }
      }
    });
  }
}
