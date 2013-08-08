package interview;

/**
 * Created with IntelliJ IDEA.
 * User: Zhisheng
 * Date: 8/7/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class GrouponPhoneII {
 /*

    // Function that tests if a string is palindrome + (BOOL)isPalindrome:(NSString*)word
// [word characterAtIndex:n]
// civic
    + (BOOL)isPalindrome:(NSString*)word {

        if (word == nil || [word isEqualsString:@""]) return false;


        int length = [word count];   // 5
        int start = 0;
        int end = length - 1; // 4

        while (end > start) {

            if ([word characterAtIndex:start] != [word characterAtIndex:end])  // i == i
            return false;
            end--;  // 2
            start++;  // 2
        }

        return true;
    }

// function that checks if a string is repeteated on a given array + (NSInteger)checkRepeatedStringOnArray:(NSArray*)array
// e.g. NSArray *arr = [[NSArray alloc] initWithObjects: @"a",@"b",@"c",@"a", nil];
// [dict objectForKey:@"somekey"];
// id someObject = 1;
    + (NSInteger)checkRepeatedStringOnArray:(NSArray*)array
    {

        NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
        for (int i = 0; i < [array count]; i++) {

        id object = array[i];

        if ([dict objectForKey:string] != nil) { //

            NSNumber *count = [dict objectForKey:string];
            if ([count intValue] == 1)
            return i;
        } else {  // havn't seen this strin
            NSNumber *count = [[NSNumber alloc] initWithInt:1];
            [dict setObject:count forKey:string];
        }
    }

        return -1;

    }




    - (NSInteger)checkRepeatedStringOnArray:(NSArray*)array
{

    NSMutableDictionary *dict = [[NSMutableDictionary alloc] init];
    for (int i = 0; i < [array count]; i++) {

        id object = array[i];

        if ([dict objectForKey:object] != nil) { //

            NSNumber *count = [dict objectForKey:object];
            if ([count intValue] == 1)
                return i;
        } else {  // havn't seen this string
            NSNumber *count = [[NSNumber alloc] initWithInt:1];
            [dict setObject:count forKey:object];
        }
    }

    return -1;
}

 */

}


