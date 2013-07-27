var directory = {

    views: {},

    models: {},

    loadTemplates: function(views, callback) {

        var deferreds = [];

        $.each(views, function(index, view) {
            if (directory[view]) {
                deferreds.push($.get('tpl/' + view + '.html', function(data) {
                    directory[view].prototype.template = _.template(data);
                }, 'html'));
            } else {
                alert(view + " not found");
            }
        });

        $.when.apply(null, deferreds).done(callback);
    }

};

directory.Router = Backbone.Router.extend({

    routes: {
        "":                 "home",
        "contact":          "contact",
        "customers/:id":    "customerDetails",
        "newCustomer":		"newCustomer"
    },

    initialize: function () {
    	_.bindAll(this);
        directory.shellView = new directory.ShellView();
        $('body').html(directory.shellView.render().el);
        
        this.$content = $("#content");
    },

    home: function () {
    	directory.homeView = new directory.HomeView();
    	directory.homeView.render();
        directory.shellView.selectMenuItem('bank-menu');
    },

    contact: function () {
        if (!directory.contactView) {
            directory.contactView = new directory.ContactView();
            directory.contactView.render();
        }
        this.$content.html(directory.contactView.el);
        
        directory.shellView.selectMenuItem('contact-menu');
    },

    customerDetails: function (id) {
    	if(directory.homeView == null)
    		this.home();
    	
        directory.homeView.renderCustomer(id);
    },
    
    newCustomer: function () {
    	if(directory.homeView == null)
    		this.home();
    	
        directory.homeView.newCustomer();
    }

});

$(document).on("ready", function () {
    directory.loadTemplates(["HomeView", "ContactView", "ShellView", "CustomerView", "CustomerSummaryView", "CustomerListItemView"],
        function () {
            directory.router = new directory.Router();
            Backbone.history.start();
        });
});
