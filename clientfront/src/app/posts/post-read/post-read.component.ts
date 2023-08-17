import { AfterViewInit, ChangeDetectorRef, Component, ElementRef, Input, OnInit, QueryList, ViewChild, ViewChildren, ViewContainerRef } from '@angular/core';
import { Post, PostResponse } from '../post.model';
import { PostService } from '../post.service';

@Component({
  selector: 'app-post-read',
  templateUrl: './post-read.component.html',
  styleUrls: ['./post-read.component.scss']
})
export class PostReadComponent implements OnInit {
  // Input property to allow parent components to pass a specific userId. If null, fetches general posts.
  @Input() userId: string | null = null;

  // Reference to the sentinel DOM element which helps determine when to fetch more posts.
  @ViewChildren('sentinel', { read: ViewContainerRef }) sentinel!: QueryList<ViewContainerRef>;

  // Array to store posts that are fetched.
  posts: Post[] = [];
  // Variable to track pagination.
  page: number = 0;
  // Flag to track if posts are currently being fetched.
  loading: boolean = false;
  // Instance of Intersection Observer to detect when sentinel is in view.
  observer!: IntersectionObserver;
  // Flag to indicate if all available posts have been fetched.
  noMorePosts: boolean = false;

  constructor(private postService: PostService, private cdRef: ChangeDetectorRef) {
    // Subscribe to postsModifiedEventEmitter to refresh data when posts are updated.
    this.postService.postsModifiedEventEmitter.subscribe(() => this.refreshData());
  }

  // Lifecycle hook to fetch initial set of posts.
  ngOnInit(): void {
    this.refreshData();
  }

  // Lifecycle hook to initialize Intersection Observer once the view is ready.
  ngAfterViewInit(): void {
    // Watch for changes in the sentinel elements.
    this.sentinel.changes.subscribe(() => {
      // If sentinel is present, initialize the Intersection Observer.
      if (this.sentinel.first) {
        this.initializeObserver();
      }
    });

    // In case the sentinel is already in the view when the component is initialized.
    if (this.sentinel.first) {
      this.initializeObserver();
    }
  }

  // Method to set up the Intersection Observer.
  initializeObserver(): void {
    setTimeout(() => {
      // Ensure sentinel is still present.
      if (this.sentinel.first) {
        // Initialize Intersection Observer to watch the sentinel.
        this.observer = new IntersectionObserver((entries) => {
          // Load more data if sentinel is in view, not currently loading, and there are more posts to fetch.
          if (entries[0].isIntersecting && !this.loading && !this.noMorePosts) {
            this.loadMoreData();
          }
        });
        // Start observing the sentinel.
        this.observer.observe(this.sentinel.first.element.nativeElement);
      }
    }, 1000); // Delay of 1 second to allow other potential DOM changes to settle.
  }

  // Method to reset and fetch the initial set of posts.
  refreshData(): void {
    // Reset page number.
    this.page = 0;
    // Reset noMorePosts flag.
    this.noMorePosts = false;

    // Depending on whether a userId is provided, fetch user-specific posts or general posts.
    const posts$ = this.userId
      ? this.postService.getPostsByUser(this.userId, this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}])
      : this.postService.getPosts(this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}]);

    // Subscribe to the Observable and update the posts array.
    posts$.subscribe(postResponse => {
      this.posts = postResponse.content;
      this.page++;
    });
  }

  // Method to fetch more posts when sentinel is in view.
  async loadMoreData(): Promise<void> {
    // Set loading to true.
    this.loading = true;

    // Depending on whether a userId is provided, fetch user-specific posts or general posts.
    const posts$ = this.userId
      ? this.postService.getPostsByUser(this.userId, this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}])
      : this.postService.getPosts(this.page, 5, [{property: 'id', direction: 'desc'}, {property: 'title', direction: 'desc'}]);

    // Subscribe to the Observable.
    posts$.subscribe(async postResponse => {
      // If no more posts are returned, update flags.
      if (postResponse.content.length === 0) {
        this.loading = false;
        this.noMorePosts = true;
      } else {
        // Update the posts array by appending the new posts.
        this.posts = [...this.posts, ...postResponse.content];
        this.page++;
        this.loading = false;

        // Manually trigger Angular's change detection.
        this.cdRef.detectChanges();
        // Wait for the DOM to update.
        await new Promise(resolve => setTimeout(resolve, 0));

        // If the sentinel is still in view after loading more posts, fetch again.
        const sentinelRect = this.sentinel.first.element.nativeElement.getBoundingClientRect();
        if (sentinelRect.top <= window.innerHeight && sentinelRect.bottom >= 0 && !this.loading) {
          this.loadMoreData();
        }
      }
    });
  }
}
