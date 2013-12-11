// Tree format definition
var LEFTNAV_FORMAT = [
	//  0. left position
	10,
	//  1. top position
	80,
	//  2. show buttons ("+" and "-" images)
	false,
	//  3. button images: collapsed state, expanded state, blank image
	["", "", "images/blank.gif"],
	//  4. size of buttons: width, height, indent amount for childless nodes
	[0, 0, 0],
	//  5. show icons ("folder" and "document")
	false,
	//  6. icon images: closed folder, opened folder, document
	["images/closed_folder.gif", "images/opened_folder.gif", "images/document.gif"],
	//  7. size of icons: width, height
	[16, 16],
	//  8. indent amount for each level of the tree
	[0, 8, 32, 48, 64, 80, 96, 112, 128],
	//  9. background color for the tree
	"",
	// 10. default CSS class for nodes
	"treeNode",
	// 11. individual CSS classes for levels of the tree
	[],
	// 12. "single branch" mode
	true,
	// 13. padding and spacing values for all nodes
	[0, 0]
];

/* // From cool tree distro
var LEFTNAV_FORMAT = [
	//  0. left position
	10,
	//  1. top position
	80,
	//  2. show buttons ("+" and "-" images)
	true,
	//  3. button images: collapsed state, expanded state, blank image
	["images/collapsed_button.gif", "images/expanded_button.gif", "images/blank.gif"],
	//  4. size of buttons: width, height, indent amount for childless nodes
	[16, 16, 16],
	//  5. show icons ("folder" and "document")
	false,
	//  6. icon images: closed folder, opened folder, document
	["", "", ""],
	//  7. size of icons: width, height
	[0, 0],
	//  8. indent amount for each level of the tree
	[0, 16, 32, 48, 64, 80, 96, 112, 128],
	//  9. background color for the tree
	"",
	// 10. default CSS class for nodes
	"treeNode",
	// 11. individual CSS classes for levels of the tree
	[],
	// 12. "single branch" mode
	true,
	// 13. padding and spacing values for all nodes
	[0, 0]
];
*/

