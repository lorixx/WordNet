package interview;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 9/5/13
 * Time: 1:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class TripAdvisorPhoneII {

    /*


#import ReviewTableViewCell.h

NSArray *reviews;
// review.title, review.date, review.username, review.reviewBody


Title on the first line
date and username on the secondline
review below that


    - (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {

        Review *review = reviews[indexPath.row];

        static NSString *identifier = @"review table view cell";

        ReviewTableViewCell *cell = [self.tableview cellForIdentifier:identifier];

        if (!cell) {
            cell = [[ReviewTabelCell alloc]initWithattri:  withResueIdentifier:identifier];
            cell.reviewBodyLabel.multipleLine = YES;
            cell.titleLabel = [[UILabel alloc]init];
            cell.titleLabel.maxWidth = TITLE_LABEL_MAX_WIDTH;
            [cell addSubView:titleLabel atLocation:CGPoint()]; //

            cell.

        }

        cell.titleLabel.text = review.title;
        cell.dataLabel.text = review.date;
        cell.usernameLabel.text = review.username;

        CGRect rect = [Utility rectForString:reviewBody withMaxWidth: MAX_WIDTH withMaxHeight:MAX_HEIGHT];

        cell.reviewBodyLabel.frame = CGRectMake(cell.reviewBodyLabel.frame.x, cell.reviewBodyLabel.frame.y, rect.width, rect.height);
        cell.reviewBodyLabel.text = review.reviewBody;

        return cell;

    }

    - (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
    {
        Review *review = reviews[indexPath.row];

        // 1. get height for the review
        float reviewBodyHeight = [Utility getHeightForString:review.reviewBody withMaxWidth: MAX_WIDTH withMaxHeight:MAX_HEIGHT];

        // 2. add padding
        return reviewBodyHeight - MIN_REVIEW_BODY_HEIGHT > 0 ? DEFAULT_HEIGHT + (reviewBodyHeight - MIN_REVIEW_BODY_HEIGHT) ? DEFAULT_HEIGHT;
    }




//
    ReviewTableViewCell.m

    @interface ReviewTableViewCell

    @property(nonatomic, strong) IBOutlet UILabel *titleLabel;
    @property(nonatomic, strong) IBOutlet UILabel *dateLabel;
    @property(nonatomic, strong) IBOutlet UILabel *usernameLabel;
    @property(nonatomic, strong) IBOutlet UILabel *reviewBodyLabel;

    @end


     */
}
